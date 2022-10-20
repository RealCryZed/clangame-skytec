package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClanService {

    // other methods

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
}
