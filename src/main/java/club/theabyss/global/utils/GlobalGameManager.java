package club.theabyss.global.utils;

import club.theabyss.TheAbyssManager;

public class GlobalGameManager {

    public static long getNowDay() {
        return TheAbyssManager.getInstance().serverCore().serverGameManager() != null ? TheAbyssManager.getInstance().serverCore().serverGameManager().day() : 0;
    }

    public static boolean isBloodMoonActive() {
        return TheAbyssManager.getInstance().serverCore().serverGameManager() != null && TheAbyssManager.getInstance().serverCore().serverGameManager().bloodMoonManager().isActive();
    }

}
