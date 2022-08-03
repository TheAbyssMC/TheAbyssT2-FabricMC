package club.theabyss.global.utils;

import club.theabyss.TheAbyssManager;

public class GlobalGameManager {

    public static long getNowDay() {
        return TheAbyssManager.getInstance().serverManager().serverGameManager() != null ? TheAbyssManager.getInstance().serverManager().serverGameManager().day() : 0;
    }

    public static boolean isBloodMoonActive() {
        return TheAbyssManager.getInstance().serverManager().serverGameManager() != null && TheAbyssManager.getInstance().serverManager().serverGameManager().bloodMoonManager().isActive();
    }

}
