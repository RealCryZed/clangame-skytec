package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TaskService extends Thread {

    private Integer taskId;
    private Integer clanId;
    private Integer reward;
    private static Lock lock = new ReentrantLock();

    public TaskService(Integer taskId, Integer clanId, Integer reward) {
        this.taskId = taskId;
        this.clanId = clanId;
        this.reward = reward;
    }

    @Override
    public void run() {
        lock.lock();
        try {
            System.err.println("Thread started... Working with: " + Thread.currentThread().getName());
            Clan clan = ClanService.getClan(clanId);
            ClanGoldAdder.updateGoldValue(clan, reward);
            saveTaskTransaction(taskId, clan, reward);
            System.err.println("Gold is saved successfully!");
        } finally {
            lock.unlock();
        }
    }

    private void saveTaskTransaction(Integer taskId, Clan clan, Integer reward) {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JdbcConnection.getConnection();

            ps = con.prepareStatement(
                    "insert into gold_from_task" +
                            "(task_id, clan_id, initial_gold_value, final_gold_value, added_gold)" +
                            " values(?, ?, ?, ?, ?);");
            ps.setInt(1, taskId);
            ps.setInt(2, clan.getId());
            ps.setInt(3, clan.getGold());
            ps.setInt(4, clan.getGold() + reward);
            ps.setInt(5, reward);
            ps.execute();

            System.out.println("Task transaction: " + taskId + ", " + clan.getGold() + ", " + reward);
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