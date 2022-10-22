package clangame.service;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import java.sql.*;

public class ClanGoldService {

    // Takes Clan object and amount of gold that needs to be added to the clan's wallet and
    // saves it in the database
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