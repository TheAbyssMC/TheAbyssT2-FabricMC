package club.theabyss.global.utils;

import club.theabyss.TheAbyssManager;

public class GlobalGameManager {

    public static long getNowDay() {
        return TheAbyssManager.getInstance().serverCore().serverGameManager() != null ? TheAbyssManager.getInstance().serverCore().serverGameManager().day() : 0;
    }

}
