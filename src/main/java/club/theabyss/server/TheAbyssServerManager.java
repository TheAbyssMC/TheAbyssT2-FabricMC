package club.theabyss.server;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.global.utils.customGlyphs.Animation;
import club.theabyss.server.global.utils.customGlyphs.NoFramesException;
import club.theabyss.server.data.DataManager;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.deathmessages.DeathMessagesManager;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class TheAbyssServerManager {

    private final TheAbyssManager core;

    private DataManager dataManager;
    private ServerGameManager serverGameManager;
    private DeathMessagesManager deathMessagesManager;
    private SkillTreeManager skillTreeManager;

    private MinecraftServer minecraftServer;

    public TheAbyssServerManager(final TheAbyssManager core) {
        this.core = core;

        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            this.minecraftServer = server;

            try {
                this.dataManager = new DataManager(core, server);
            } catch (Exception e) {
                e.printStackTrace();
            }

            this.serverGameManager = new ServerGameManager(this, server);
            this.deathMessagesManager = new DeathMessagesManager(this);
            this.skillTreeManager = new SkillTreeManager(this);

            for (String animation : new String[] {
                    "bloodmoonanimation.json"
            }) {
                try {
                    Animation.load(animation);
                } catch (NoFramesException e) {
                    e.printStackTrace();
                }
            }

            serverGameManager.bloodMoonManager().load();

            TheAbyssManager.getLogger().info("The mod's server has been loaded successfully.");
        }));
        ServerLifecycleEvents.SERVER_STOPPED.register((server -> {
            try {
                dataManager.save();
            } catch (Exception e) {
                e.printStackTrace();
            }

            var bloodMoonManager = serverGameManager.bloodMoonManager();

            bloodMoonManager.cancelBossBarTask(server);

            TheAbyssManager.getLogger().info("The mod's server has been unloaded successfully.");
        }));
    }

    /**
     * @return the mod core.
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
     * @return the current Minecraft Server.
     */
    public MinecraftServer minecraftServer() {
        return minecraftServer;
    }

    /**
     * @return the skill tree manager.
     */
    public SkillTreeManager skillTreeManager() {
        return skillTreeManager;
    }

    /**
     * @return the ServerGameManager.
     */
    public ServerGameManager serverGameManager() {
        return serverGameManager;
    }

    /**
     * @return the DeathMessagesManager.
     */
    public DeathMessagesManager deathMessagesManager() {
        return deathMessagesManager;
    }

}
