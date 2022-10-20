package clangame.service;

import clangame.model.Clan;

public class UserAddGoldService {

    public void addGoldToClan(Integer userId, Integer clanId, int gold) {
        Clan clan = ClanService.getClan(clanId);
        Thread clanService = new ClanGoldAdder(clan, gold);
        clanService.start();
//        try {
//            clanService.join(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        GoldTrackerService.saveDepositTransaction(userId, clan, gold);
    }
}
