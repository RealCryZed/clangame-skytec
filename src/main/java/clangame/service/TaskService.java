package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TaskService extends Thread {

    private Integer taskId;
    private Integer clanId;
    private Integer reward;

    public TaskService(Integer taskId, Integer clanId, Integer reward) {
        this.taskId = taskId;
        this.clanId = clanId;
        this.reward = reward;
    }

    // When started it gets clan from database using given id and then
    // calls method ClanGoldAdder.updateGoldValue() to add gold to the wallet.
    // Then it calls saveDepositTransaction() to save the transaction
    @Override
    public void run() {
        Clan clan = ClanService.getClan(clanId);
        ClanGoldAdder.updateGoldValue(clan, reward);
        saveTaskTransaction(taskId, clan, reward);
    }

    // Saves the completed transaction in the database 'gold_from_task'
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

            System.out.println("Task transaction: taskId=" + taskId + ", clanId=" + clan.getId() +
                    ", initialGold=" + clan.getGold() + ", addedGold=" + reward + "\n");
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