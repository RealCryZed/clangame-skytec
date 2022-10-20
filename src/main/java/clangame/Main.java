package clangame;

import clangame.config.JdbcConnection;
import clangame.model.Clan;
import clangame.service.ClanService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        initializeTable();

        Thread thread1 = new ClanService(1, 550);
        Thread thread2 = new ClanService(2, 1000);
        Thread thread3 = new ClanService(1, 60);
        Thread thread4 = new ClanService(3, 480);
        Thread thread5 = new ClanService(3, 20);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();

        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        thread5.join();

//        Clan clan = ClanService.getClan(1);
//        System.out.println(clan);
//        ClanService.updateGoldValue(clan, 999);
//        clan = ClanService.getClan(1);
//        System.out.println(clan);
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
                    "insert into clans values(1, 'Clan1', 0);" +
                    "insert into clans values(2, 'Clan2', 0);" +
                    "insert into clans values(3, 'Clan3', 0);";

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
