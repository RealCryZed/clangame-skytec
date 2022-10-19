package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import java.sql.*;

public class ClanService {

    public static Clan getClan(Integer clanId) {
        Clan clan = new Clan();
        Connection con = null;
        try {
            con = JdbcConnection.getConnection();

            String query = "SELECT id, name, gold FROM clans WHERE id = " + clanId;

            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                clan.setId(rs.getInt("id"));
                clan.setName(rs.getString("name"));
                clan.setGold(rs.getInt("gold"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return clan;
    }
}