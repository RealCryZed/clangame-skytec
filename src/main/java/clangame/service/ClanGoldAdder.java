package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import java.sql.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClanGoldAdder extends Thread {

    private Clan clan;
    private Integer depositGold;
    private static Lock lock = new ReentrantLock();

    public ClanGoldAdder(Clan clan, Integer depositGold) {
        this.clan = clan;
        this.depositGold = depositGold;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.err.println("Thread started... Working with: " + Thread.currentThread().getName());
            updateGoldValue(depositGold);
        } finally {
            lock.unlock();
        }
    }

    private void updateGoldValue(int depositGold) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JdbcConnection.getConnection();
            int actualGold = clan.getGold();

            ps = con.prepareStatement("UPDATE clans set gold = ? WHERE id = ?");
            ps.setInt(1, actualGold + depositGold);
            ps.setInt(2, clan.getId());
            ps.execute();
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