package club.theabyss.server.data;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.Instantiable;
import club.theabyss.server.TheAbyssServerManager;
import net.minecraft.server.MinecraftServer;

public class DataManager extends Instantiable<TheAbyssServerManager> {

    private final JsonConfig gameDataConfig;
    private final JsonConfig deathMessages;
    private final JsonConfig skillTree;

    public DataManager(final TheAbyssManager core, MinecraftServer server) throws Exception {
        super(core.serverCore());

        final String folderName = "theabyssmanager";

        this.gameDataConfig = (server.isDedicated()) ? JsonConfig.serverConfig("gameDataConfig.json", folderName, server) : JsonConfig.savesConfig("gameDataConfig.json", folderName, server);
        this.deathMessages = (server.isDedicated()) ? JsonConfig.serverConfig("deathMessages.json", folderName, server) : JsonConfig.savesConfig("deathMessages.json", folderName, server);
        this.skillTree = (server.isDedicated()) ? JsonConfig.serverConfig("skillTree.json", folderName, server) : JsonConfig.savesConfig("skillTree.json", folderName, server);
    }

    /**
     * A method that saves the current state of the application to json files.
     */
    public void save() {
        instance().serverGameManager().save(gameDataConfig);
        instance().deathMessagesManager().save(deathMessages);
        instance().skillTreeManager().save(skillTree);
    }

    /**
     * @return the gameDataConfig.
     */
    public JsonConfig gameDataConfig() {
        return this.gameDataConfig;
    }

    /**
     * @return the skill tree.
     */
    public JsonConfig skillTree() {
        return skillTree;
    }

    /**
     * @return the death messages.
     */
    public JsonConfig deathMessages() {
        return this.deathMessages;
    }

}
