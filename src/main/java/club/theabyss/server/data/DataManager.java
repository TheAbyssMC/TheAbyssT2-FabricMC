package club.theabyss.server.data;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.Instantiable;
import club.theabyss.server.TheAbyssServerManager;
import net.minecraft.server.MinecraftServer;

public class DataManager extends Instantiable<TheAbyssServerManager> {

    private final JsonConfig gameDataConfig;

    public DataManager(final TheAbyssManager core, MinecraftServer server) throws Exception {
        super(core.serverCore());
        if (!server.isDedicated()) {
            this.gameDataConfig = JsonConfig.savesConfig("gameDataConfig.json", server);
        } else {
            this.gameDataConfig = JsonConfig.svConfig("gameDataConfig.json", server);
        }
    }

    /**
     * A method that saves the current state of the application to json files.
     */
    public void save() {
        instance().serverGameManager().save(gameDataConfig);
    }

    /**
     * @return the gameDataConfig.
     */
    public JsonConfig gameDataConfig() {
        return this.gameDataConfig;
    }

}
