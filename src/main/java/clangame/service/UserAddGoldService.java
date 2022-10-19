package clangame.service;

import clangame.model.Clan;

public class UserAddGoldService {

    public void addGoldToClan(Long userId, Integer clanId, int gold) {
        Clan clan = ClanService.getClan(clanId);
        //clan.[gold] += gold;
        //как-то сохранить изменения
    }
}
