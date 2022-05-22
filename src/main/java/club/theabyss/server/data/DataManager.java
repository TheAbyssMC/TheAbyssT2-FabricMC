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
        if (!server.isDedicated()) {
            this.gameDataConfig = JsonConfig.savesConfig("gameDataConfig.json", server);
            this.deathMessages = JsonConfig.savesConfig("deathMessages.json", server);
            this.skillTree = JsonConfig.savesConfig("skillTree.json", server);
        } else {
            this.gameDataConfig = JsonConfig.serverConfig("gameDataConfig.json", server);
            this.deathMessages = JsonConfig.serverConfig("deathMessages.json", server);
            this.skillTree = JsonConfig.serverConfig("skillTree.json", server);
        }
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
