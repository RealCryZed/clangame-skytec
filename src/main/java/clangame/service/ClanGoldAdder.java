package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import java.sql.*;

public class ClanGoldAdder {

    public static void updateGoldValue(Clan clan, int depositGold) {
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