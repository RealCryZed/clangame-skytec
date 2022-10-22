package clangame;

import clangame.service.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        DBService.initializeTables();
        simulateGoldAddition();
        DBService.getDbInfo();
    }

    // Simulation of adding gold from 2 different services at the same time
    private static void simulateGoldAddition() {
        ExecutorService depositExecService = Executors.newSingleThreadExecutor();
        ExecutorService taskExecService = Executors.newSingleThreadExecutor();

        try {
            for (int i = 1; i <= 20; i++) {
                Random random = new Random();

                int randomService = random.nextInt(2);
                Integer randomClanId = random.nextInt(3) + 1;
                Integer randomGoldValue = random.nextInt(1000 - 10) + 10;

                Thread thread;
                if (randomService == 0) {
                    thread = new TaskService(i, randomClanId, randomGoldValue);
                    taskExecService.submit(thread);
                } else {
                    thread = new UserAddGoldService(i, randomClanId, randomGoldValue);
                    depositExecService.submit(thread);
                }
            }
        } finally {
            depositExecService.shutdown();
            taskExecService.shutdown();
        }
    }
}
