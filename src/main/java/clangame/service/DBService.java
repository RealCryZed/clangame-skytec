package clangame.service;

import clangame.config.JdbcConnection;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DBService {

    // Initialization of tables in PostgreSQL on external server
    public static void initializeTables() {
        Connection con = null;
        Statement statement = null;
        try {
            con = JdbcConnection.getConnection();

            String clansInitializationQuery =
                    "drop table if exists clans;" +
                            "create table clans (\n" +
                            "    id int PRIMARY KEY,\n" +
                            "    name VARCHAR(50) not null unique,\n" +
                            "    gold int not null\n" +
                            ");" +
                            "insert into clans values(1, 'Clan1', 0);" +
                            "insert into clans values(2, 'Clan2', 0);" +
                            "insert into clans values(3, 'Clan3', 0);";

            String goldFromTaskInitializationQuery =
                    "drop table if exists gold_from_task;" +
                            "create table gold_from_task (\n" +
                            "    transaction_id int PRIMARY KEY generated always as identity,\n" +
                            "    task_id int not null,\n" +
                            "    clan_id int not null,\n" +
                            "    initial_gold_value int not null,\n" +
                            "    final_gold_value int not null,\n" +
                            "    added_gold int not null, \n" +
                            "    date_time VARCHAR(100) not null);";

            String goldDonationInitializationQuery =
                    "drop table if exists gold_from_donation;" +
                            "create table gold_from_donation (\n" +
                            "    transaction_id int PRIMARY KEY generated always as identity,\n" +
                            "    user_id int not null,\n" +
                            "    clan_id int not null,\n" +
                            "    initial_gold_value int not null,\n" +
                            "    final_gold_value int not null,\n" +
                            "    added_gold int not null, \n" +
                            "    date_time VARCHAR(100) not null);";

            statement = con.createStatement();

            statement.execute(clansInitializationQuery);
            statement.execute(goldFromTaskInitializationQuery);
            statement.execute(goldDonationInitializationQuery);

            System.out.println("All tables initialized!\n");
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

    public static void getDbInfo() {
        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Type 'tasks' to show all transactions made by completing tasks.");
            System.out.println("Type 'deposits' to show all transactions made by donations.");
            System.out.println("Type 'clans' to show all clans.");
            System.out.println();

            String userValue = scanner.nextLine();
            switch (userValue.toLowerCase()) {
                case "tasks":
                    DBService.getAllTaskTransactions();
                    break;
                case "deposits":
                    DBService.getAllDepositTransactions();
                    break;
                case "clans":
                    DBService.getAllClans();
                    break;
                default:
                    System.out.println("Try again!\n");
                    break;
            }
        }
    }

    private static void getAllClans() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM clans");

            while (rs.next()) {
                System.out.println("id: " + rs.getInt(1) + ", clanName: "
                        + rs.getString(2) + ", gold: " + rs.getString(3));
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getAllTaskTransactions() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM gold_from_task");

            while (rs.next()) {
                System.out.println("transactionId: " + rs.getInt(1) + ", taskId: "
                        + rs.getInt(2) + ", clanId: " + rs.getInt(3) + ", initialGold: "
                        + rs.getInt(4) + ", finalGold: " + rs.getInt(5) + ", addedGold: "
                        + rs.getInt(6) + ", Date: '" + rs.getString(7) + "'");
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void getAllDepositTransactions() {
        Connection con = null;
        Statement st = null;
        ResultSet rs = null;
        try {
            con = JdbcConnection.getConnection();

            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM gold_from_donation");

            while (rs.next()) {
                System.out.println("transactionId: " + rs.getInt(1) + ", userId: "
                        + rs.getInt(2) + ", clanId: " + rs.getInt(3) + ", initialGold: "
                        + rs.getInt(4) + ", finalGold: " + rs.getInt(5) + ", addedGold: "
                        + rs.getInt(6) + ", Date: '" + rs.getString(7) + "'");
            }
            System.out.println();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if(rs != null)
                    rs.close();
                if(st != null)
                    st.close();
                if(con != null)
                    con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
