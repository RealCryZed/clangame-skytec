package clangame;

import clangame.config.JdbcConnection;
import clangame.service.TaskService;
import clangame.service.UserAddGoldService;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void main(String[] args) {
        initializeTables();

        UserAddGoldService userAddGoldService = new UserAddGoldService();
        TaskService taskService = new TaskService();

        taskService.completeTask(1, 2, 30);
        System.err.println("adding gold");
        taskService.completeTask(2, 3, 50);
        System.err.println("adding gold");
        userAddGoldService.addGoldToClan(1, 1, 2000);
        System.err.println("adding gold");
        taskService.completeTask(3, 1, 50);
        System.err.println("adding gold");
        taskService.completeTask(4, 1, 20);
        System.err.println("adding gold");
        userAddGoldService.addGoldToClan(2, 2, 500);
        System.err.println("adding gold");
        taskService.completeTask(5, 3, 70);
        System.err.println("adding gold");
        taskService.completeTask(6, 3, 100);
        System.err.println("adding gold");
        userAddGoldService.addGoldToClan(3, 3, 100);
        System.err.println("adding gold");
        taskService.completeTask(7, 2, 10);
        System.err.println("adding gold");
        userAddGoldService.addGoldToClan(4, 1, 100);
        System.err.println("adding gold");
        taskService.completeTask(8, 2, 30);
        System.err.println("adding gold");
        taskService.completeTask(9, 1, 70);
        System.err.println("adding gold");
        userAddGoldService.addGoldToClan(5, 3, 400);
        System.err.println("adding gold");
        taskService.completeTask(10, 1, 20);
        System.err.println("adding gold");
    }

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
                    "    added_gold int not null);";

            String goldDonationInitializationQuery =
                    "drop table if exists gold_from_donation;" +
                    "create table gold_from_donation (\n" +
                    "    transaction_id int PRIMARY KEY generated always as identity,\n" +
                    "    user_id int not null,\n" +
                    "    clan_id int not null,\n" +
                    "    initial_gold_value int not null,\n" +
                    "    final_gold_value int not null,\n" +
                    "    added_gold int not null);";

            statement = con.createStatement();

            statement.execute(clansInitializationQuery);
            statement.execute(goldFromTaskInitializationQuery);
            statement.execute(goldDonationInitializationQuery);

            System.out.println("All tables initialized!");
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
