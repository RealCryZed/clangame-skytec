package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class UserAddGoldService extends Thread{

    private Integer userId;
    private Integer clanId;
    private Integer gold;
    private static Lock lock = new ReentrantLock();

    public UserAddGoldService(Integer userId, Integer clanId, Integer gold) {
        this.userId = userId;
        this.clanId = clanId;
        this.gold = gold;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.err.println("Thread started... Working with: " + Thread.currentThread().getName());
            Clan clan = ClanService.getClan(clanId);
            ClanGoldAdder.updateGoldValue(clan, gold);
            saveDepositTransaction(userId, clan, gold);
            System.err.println("Gold is saved successfully!");
        } finally {
            lock.unlock();
        }
    }

    private void saveDepositTransaction(Integer userId, Clan clan, Integer gold) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JdbcConnection.getConnection();

            ps = con.prepareStatement(
                    "insert into gold_from_donation" +
                            "(user_id, clan_id, initial_gold_value, final_gold_value, added_gold)" +
                            " values(?, ?, ?, ?, ?);");
            ps.setInt(1, userId);
            ps.setInt(2, clan.getId());
            ps.setInt(3, clan.getGold());
            ps.setInt(4, clan.getGold() + gold);
            ps.setInt(5, gold);
            ps.execute();
            System.out.println("Deposit transaction: " + userId + ", " + clan.getGold() + ", " + gold);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && ps != null) {
                    con.close();
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
