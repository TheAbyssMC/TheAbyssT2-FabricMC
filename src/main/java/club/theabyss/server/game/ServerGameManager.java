package club.theabyss.server.game;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.server.TheAbyssServerManager;
import club.theabyss.server.data.storage.GameData;
import club.theabyss.global.interfaces.Restorable;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import club.theabyss.server.game.entity.EntityManager;
import club.theabyss.server.global.events.GameDateEvents;
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
    private final EntityManager entityManager;

    private final ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    public ServerGameManager(final TheAbyssServerManager serverCore, MinecraftServer server) {
        this.serverCore = serverCore;
        this.server = server;

        this.restore(serverCore.dataManager().gameDataConfig());

        this.bloodMoonManager = new BloodMoonManager(serverCore);

        this.entityManager = new EntityManager(this);

        elapseDayTimer();
        autoSaveTimer(1);

        server.getWorlds().forEach(w -> w.getGameRules().get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, server));

        /*
        Reloads the pathfinders from all the loaded MobEntities. It is executed a second after the game loads
        to make sure all the entities are affected, because this class (ServerGameManager) is loaded right before
        the server is ready to tick for the first time (this means the entities are not loaded at that time).
         */
        executorService.schedule(() -> {
            EntityManager.reloadEntityPathfinders();
        }, 1, TimeUnit.SECONDS);
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
     * Creates a task that updates the mod's new time every day.
     */
    private void elapseDayTimer() {
        executorService.schedule(() -> {
            GameDateEvents.DayHasElapsedEvent.EVENT.invoker().changeDay(day());
            elapseDayTimer();
        }, 1 + ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1)), TimeUnit.MINUTES);
    }

    /**
     * Creates a task that saves all the mod's game data every {@param rate}.
     * @param rate of time which the function will be executed.
     */
    private void autoSaveTimer(int rate) {
        executorService.scheduleAtFixedRate(() -> {
            serverCore.dataManager().save();
            TheAbyssManager.getLogger().info("The mod's game data has been automatically saved.");
        }, rate, rate, TimeUnit.MINUTES);
    }

    /**
     * @return the server manager.
     */
    public TheAbyssServerManager serverManager() {
        return serverCore;
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
