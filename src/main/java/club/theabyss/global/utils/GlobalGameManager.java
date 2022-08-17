package club.theabyss.global.utils;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.game.mechanics.flashbang.FlashBangServerManager;

public class GlobalGameManager {

    public static long getNowDay() {
        return TheAbyssManager.getInstance().serverManager().serverGameManager() != null ? TheAbyssManager.getInstance().serverManager().serverGameManager().day() : 0;
    }

    public static boolean isBloodMoonActive() {
        return TheAbyssManager.getInstance().serverManager().serverGameManager() != null && TheAbyssManager.getInstance().serverManager().serverGameManager().bloodMoonManager().isActive();
    }

    public static FlashBangServerManager getFlashBangManager() {
        return TheAbyssManager.getInstance().serverManager().serverGameManager() != null ? TheAbyssManager.getInstance().serverManager().serverGameManager().flashBangManager() : null;
    }

}
