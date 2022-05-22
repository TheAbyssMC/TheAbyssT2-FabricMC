package club.theabyss.server.game;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.data.storage.GameData;
import club.theabyss.global.interfaces.Restorable;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import club.theabyss.server.global.events.GameDateEvents;
import club.theabyss.server.global.listeners.GlobalServerListeners;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.GameRules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ServerGameManager implements Restorable {

    private final TheAbyssServerManager serverCore;
    private final MinecraftServer server;

    private GameData gameData;

    private final BloodMoonManager bloodMoonManager;

    private final @Getter GlobalServerListeners globalServerListeners;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ServerGameManager(final TheAbyssServerManager serverCore, MinecraftServer server, boolean bloodMoonEnabled) {
        this.serverCore = serverCore;
        this.server = server;

        this.restore(serverCore.dataManager().gameDataConfig());

        this.globalServerListeners = new GlobalServerListeners(serverCore);
        this.bloodMoonManager = new BloodMoonManager(serverCore, bloodMoonEnabled);

        timer();

        server.getWorlds().forEach(w -> w.getGameRules().get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, server));
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

    private void timer() {
        executorService.schedule(() -> {
            GameDateEvents.DayHasElapsedEvent.EVENT.invoker().changeDay(day());
            timer();
        }, 1 + ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1)), TimeUnit.MINUTES);
    }

    /**
     * @return the GameData object.
     */
    public GameData gameData() {
        return this.gameData;
    }

    /**
     * @return the game server.
     */
    public MinecraftServer minecraftServer() {
        return this.server;
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
