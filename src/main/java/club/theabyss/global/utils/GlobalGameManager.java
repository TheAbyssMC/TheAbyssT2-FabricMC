package club.theabyss.global.utils;

import club.theabyss.TheAbyss;
import club.theabyss.server.game.mechanics.flashbang.FlashBangServerManager;

public class GlobalGameManager {

    public static long getNowDay() {
        return TheAbyss.getInstance().serverManager().serverGameManager() != null ? TheAbyss.getInstance().serverManager().serverGameManager().day() : 0;
    }

    public static boolean isBloodMoonActive() {
        return TheAbyss.getInstance().serverManager().serverGameManager() != null && TheAbyss.getInstance().serverManager().serverGameManager().bloodMoonManager().isActive();
    }

    public static FlashBangServerManager getFlashBangManager() {
        return TheAbyss.getInstance().serverManager().serverGameManager() != null ? TheAbyss.getInstance().serverManager().serverGameManager().flashBangManager() : null;
    }

}
