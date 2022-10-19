package clangame;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import clangame.service.ClanService;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        initializeTable();

        Clan clan = ClanService.getClan(1);
        System.out.println(clan);

        ClanService.updateGoldValue(clan, 999);

        clan = ClanService.getClan(1);
        System.out.println(clan);
    }

    public static void initializeTable() {
        Connection con = null;
        Statement statement = null;
        try {
            con = JdbcConnection.getConnection();

            String initializationQuery = "drop table if exists clans;" +
                    "create table clans (\n" +
                    "    id int PRIMARY KEY,\n" +
                    "    name VARCHAR(50) not null unique,\n" +
                    "    gold int not null\n" +
                    ");" +
                    "insert into clans values(1, 'Clan1', 50)";

            String testQuery = "SELECT id, name, gold FROM clans";

            statement = con.createStatement();
            statement.execute(initializationQuery);

            ResultSet rs = statement.executeQuery(testQuery);

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ", " + rs.getString("name") + ", " + rs.getInt("gold"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null && statement != null) {
                    con.close();
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
