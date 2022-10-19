package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import java.sql.*;

public class ClanService {

    public static Clan getClan(Integer clanId) {
        Clan clan = new Clan();
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JdbcConnection.getConnection();

            String query = "SELECT id, name, gold FROM clans WHERE id = " + clanId;

            ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                clan.setId(rs.getInt("id"));
                clan.setName(rs.getString("name"));
                clan.setGold(rs.getInt("gold"));
            }
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

        return clan;
    }

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