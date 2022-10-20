package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GoldTrackerService {

    public static void saveDepositTransaction(Integer userId, Clan clan, Integer gold) {
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

    public static void saveTaskTransaction(Integer taskId, Clan clan, Integer reward) {
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
