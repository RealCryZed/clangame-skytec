package clangame;

import clangame.config.JdbcConnection;
import clangame.service.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        initializeTables();

        ExecutorService depositExecService = Executors.newSingleThreadExecutor();
        ExecutorService taskExecService = Executors.newSingleThreadExecutor();
        try {
            for (int i = 1; i < 20; i++) {
                Random random = new Random();
                Integer randomClanId = random.nextInt(3) + 1;
                Integer randomGoldValue = random.nextInt(1000 - 10) + 10;
                Thread thread = new UserAddGoldService(i, randomClanId, randomGoldValue);
                depositExecService.submit(thread);
                System.out.println("depositExecService submitted");
            }

            for (int i = 1; i < 20; i++) {
                Random random = new Random();
                Integer randomClanId = random.nextInt(3 - 1);
                Integer randomGoldValue = random.nextInt(1000 - 10) + 10;
                Thread thread = new TaskService(i, randomClanId, randomGoldValue);
                taskExecService.submit(thread);
                System.out.println("taskExecService submitted");
            }
        } finally {
            depositExecService.shutdown();
            taskExecService.shutdown();
        }
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
