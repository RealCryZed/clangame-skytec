package clangame.service;

import clangame.model.Clan;

public class UserAddGoldService {

    private final ClanService clans;

    public void addGoldToClan(Long userId, Long clanId, int gold) {
        Clan clan = clans.getClan(clanId);
        //clan.[gold] += gold;
        //как-то сохранить изменения
    }
}
