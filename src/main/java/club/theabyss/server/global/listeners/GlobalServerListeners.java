package club.theabyss.server.global.listeners;

import club.theabyss.global.utils.timedTitle.TimedTitle;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.global.events.ServerPlayerConnectionEvents;
import net.minecraft.util.ActionResult;

import java.util.Date;

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

    //TODO FIXEAR ERROR DE BOSSBAR DE UNA FORMA MENOS CUTRE.
    private void onPlayerConnects() {
        ServerPlayerConnectionEvents.OnServerPlayerConnect.EVENT.register((player, server) -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            if (server.isDedicated()) {
                if (bloodMoonManager.isActive()) {
                    if (!bossBar.getPlayers().contains(player)) {
                        bossBar.addPlayer(player);
                    }
                }
            } else {
                bloodMoonManager.load();
            }

            var name = player.getName().asString();
            var queue = TimedTitle.titleQueue.get(name);
            if (queue != null) {
                if (queue.task == null) TimedTitle.processTitles(name);
            }

            return ActionResult.PASS;
        });
    }

    private void onPlayerDisconnects() {
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.register((player, server) -> {
            var bloodMoonManager = serverCore.serverGameManager().bloodMoonManager();
            var bossBar = bloodMoonManager.getBloodMoonListener().bossBar();

            bossBar.removePlayer(player);

            String name = player.getName().asString();
            TimedTitle.Queue queue = TimedTitle.titleQueue.get(name);
            if (!(queue == null || queue.task == null || queue.titles.size() == 0)) {
                queue.task.cancel();
                queue.task = null;
                long timePassed = new Date().getTime() - queue.lastTask;
                TimedTitle.Title current = queue.titles.get(0);
                if (timePassed >= current.fadeIn) {
                    current.fadeIn = 0;
                    if (timePassed < current.minimumStayTime) { // Should never happen
                        current.minimumStayTime -= timePassed;
                        current.stayTime -= timePassed;
                    } else {
                        queue.titles.remove(0);
                    }
                } else {
                    current.fadeIn -= timePassed; // This is not a perfect solution, but is the best possible one
                }
            }

            return ActionResult.PASS;
        });
    }

}
