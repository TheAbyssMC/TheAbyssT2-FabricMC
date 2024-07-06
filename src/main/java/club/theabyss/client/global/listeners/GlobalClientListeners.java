package club.theabyss.client.global.listeners;

import club.theabyss.TheAbyss;
import club.theabyss.client.global.events.ClientStateEvents;
import net.minecraft.util.ActionResult;

public class GlobalClientListeners {

    public static void init() {
        onClientPause();
        onClientResume();
    }

    private static void onClientPause() {
        ClientStateEvents.OnClientPause.EVENT.register(client -> {
            var serverGameManager = TheAbyss.getInstance().serverManager().serverGameManager();

            if (serverGameManager == null) return ActionResult.FAIL;

            if (serverGameManager.minecraftServer().isDedicated()) return ActionResult.PASS;

            serverGameManager.bloodMoonManager().pauseBloodMoon();
            serverGameManager.bloodMoonManager().pauseNaturalBloodMoon();

            return ActionResult.PASS;
        });
    }

    private static void onClientResume() {
        ClientStateEvents.OnClientResume.EVENT.register(client -> {
            var serverGameManager = TheAbyss.getInstance().serverManager().serverGameManager();

            if (serverGameManager == null) return ActionResult.FAIL;

            if (serverGameManager.minecraftServer().isDedicated()) return ActionResult.PASS;

            serverGameManager.bloodMoonManager().resumeBloodMoon();
            serverGameManager.bloodMoonManager().resumeNaturalBloodMoon();

            return ActionResult.PASS;
        });
    }

}
