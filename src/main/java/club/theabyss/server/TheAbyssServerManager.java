package club.theabyss.server;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.data.DataManager;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.global.listeners.GlobalServerListeners;
import lombok.Getter;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

public class TheAbyssServerManager {

    private final TheAbyssManager core;

    private DataManager dataManager;
    private ServerGameManager serverGameManager;

    private @Getter GlobalServerListeners globalServerListeners;

    private boolean bloodMoonEnabled = false;
    private boolean globalEnabled = false;

    public TheAbyssServerManager(final TheAbyssManager core) {
        this.core = core;

        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            try {
                this.dataManager = new DataManager(core, server.getCommandSource().getServer());
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.serverGameManager = new ServerGameManager(this, server.getCommandSource().getServer(), bloodMoonEnabled);
            bloodMoonEnabled = true;

            this.globalServerListeners = new GlobalServerListeners(this).load(globalEnabled);
            globalEnabled = true;

            serverGameManager.bloodMoonManager().load();

            TheAbyssManager.getLogger().info("The server has been loaded successfully.");
        }));
        ServerLifecycleEvents.SERVER_STOPPED.register((server -> {
            try {
                dataManager.save();
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.serverGameManager = null;

            TheAbyssManager.getLogger().info("The server has been unloaded successfully.");
        }));
    }

    /**
     * @return the core.
     */
    public TheAbyssManager core() {
        return core;
    }

    /**
     * @return the DataManager.
     */
    public DataManager dataManager() {
        return dataManager;
    }

    /**
     * @return the ServerGameManager.
     */
    public ServerGameManager serverGameManager() {
        return serverGameManager;
    }

}
