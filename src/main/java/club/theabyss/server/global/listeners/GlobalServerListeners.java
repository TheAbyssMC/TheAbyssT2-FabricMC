package club.theabyss.server.global.listeners;

import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.global.events.ServerPlayerConnectionEvents;
import net.minecraft.util.ActionResult;

public class GlobalServerListeners {

    private final TheAbyssServerManager serverCore;

    public GlobalServerListeners(final TheAbyssServerManager serverCore) {
        this.serverCore = serverCore;
    }

    public GlobalServerListeners load(boolean globalEnabled) {
        if (!globalEnabled) {
            registerCallbacks();
        }
        return this;
    }

    private void registerCallbacks() {
        onPlayerConnects();
        onPlayerDisconnects();
    }

    private void onPlayerConnects() {
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register(player -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            if (bloodMoonManager.isActive()) {
                if (!bossBar.getPlayers().contains(player))
                    bossBar.addPlayer(player);
            }

            return ActionResult.PASS;
        });
    }

    private void onPlayerDisconnects() {
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register(player -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            if (bossBar.getPlayers().contains(player))
                bossBar.removePlayer(player);

            return ActionResult.PASS;
        });
    }

}
