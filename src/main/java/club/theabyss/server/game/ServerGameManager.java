package club.theabyss.server.game;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.data.types.GameData;
import club.theabyss.global.interfaces.Restorable;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import club.theabyss.server.global.listeners.GlobalServerListeners;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;

public class ServerGameManager implements Restorable {

    private final TheAbyssServerManager serverCore;
    private final @Getter MinecraftServer server;

    private GameData gameData;

    private final BloodMoonManager bloodMoonManager;
    private final @Getter GlobalServerListeners globalServerListeners;

    public ServerGameManager(final TheAbyssServerManager serverCore, MinecraftServer server, boolean bloodMoonEnabled) {
        this.serverCore = serverCore;
        this.server = server;

        this.restore(serverCore.dataManager().gameDataConfig());

        this.globalServerListeners = new GlobalServerListeners(serverCore);
        this.bloodMoonManager = new BloodMoonManager(serverCore, bloodMoonEnabled);
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.gameData = new GameData(new BloodMoonData());
        } else {
            this.gameData = TheAbyssManager.gson().fromJson(jsonConfig.getJsonObject(), GameData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyssManager.gson().toJsonTree(gameData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the GameData object.
     */
    public GameData gameData() {
        return this.gameData;
    }

    /**
     * @return the BloodMoonManager object.
     */
    public BloodMoonManager bloodMoonManager() {
        return this.bloodMoonManager;
    }

    /**
     * @return the day of the Game.
     */
    public long day() {
        return gameData.day();
    }

}
