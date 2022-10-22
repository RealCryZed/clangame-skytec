package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserAddGoldService extends Thread{

    private Integer userId;
    private Integer clanId;
    private Integer gold;

    public UserAddGoldService(Integer userId, Integer clanId, Integer gold) {
        this.userId = userId;
        this.clanId = clanId;
        this.gold = gold;
    }

    // When started it gets clan from database using given id and then
    // calls method ClanGoldAdder.updateGoldValue() to add gold to the wallet.
    // Then it calls saveDepositTransaction() to save the transaction
    @Override
    public void run() {
        Clan clan = ClanService.getClan(clanId);
        ClanGoldService.updateGoldValue(clan, gold);
        saveDepositTransaction(userId, clan, gold);
    }

    // Saves the completed transaction in the database 'gold_from_donation'
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
            System.out.println("Deposit transaction: userId=" + userId + ", clanId=" + clan.getId() +
                    ", initialGold=" + clan.getGold() + ", addedGold=" + gold + "\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(ps != null)
                    ps.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
