package clangame.service;

import clangame.model.Clan;

public class TaskService {

    public void completeTask(Integer taskId, Integer clanId, Integer reward) {
        // if (true) for show purposes
        if(true) {
            Clan clan = ClanService.getClan(clanId);
            Thread clanService = new ClanGoldAdder(clan, reward);
            clanService.start();
//            try {
//                clanService.join(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            GoldTrackerService.saveTaskTransaction(taskId, clan, reward);
        }
    }
}