package club.theabyss.server.game;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.TheAbyssServer;
import club.theabyss.server.data.storage.GameData;
import club.theabyss.server.game.bloodmoon.BloodMoonManager;
import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import club.theabyss.server.game.entity.EntityManager;
import club.theabyss.server.game.mechanics.flashbang.FlashBangServerManager;
import club.theabyss.server.game.totem.TotemManager;
import club.theabyss.server.global.events.GameDateEvents;
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

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class ServerGameManager implements Restorable {

    private final TheAbyssServer theAbyssServer;
    private final MinecraftServer server;

    private GameData gameData;

    private final BloodMoonManager bloodMoonManager;
    private final EntityManager entityManager;

    private final TotemManager totemManager;

    private final FlashBangServerManager flashBangManager;

    private @Getter ScheduledExecutorService serverGameExecutorService;

    public ServerGameManager(final TheAbyssServer theAbyssServer, MinecraftServer server) {
        this.theAbyssServer = theAbyssServer;
        this.server = server;

        serverGameExecutorService = Executors.newSingleThreadScheduledExecutor();

        this.restore(theAbyssServer.dataManager().gameData());

        this.bloodMoonManager = new BloodMoonManager(theAbyssServer);
        this.entityManager = new EntityManager(this);
        this.totemManager = new TotemManager(this);
        this.flashBangManager = new FlashBangServerManager(this);

        elapseDayTimer();
        autoSaveTimer(1);

        server.getWorlds().forEach(w -> w.getGameRules().get(GameRules.DO_IMMEDIATE_RESPAWN).set(true, server));

        /*
        Reloads the pathfinders from all the loaded MobEntities. It is executed a second after the game loads
        to make sure all the entities are affected, because this class (ServerGameManager) is loaded right before
        the server is ready to tick for the first time (this means the entities are not loaded at that time).
         */
        serverGameExecutorService.schedule(EntityManager::reloadEntities, 1, TimeUnit.SECONDS);
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.gameData = new GameData(new BloodMoonData());
        } else {
            this.gameData = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), GameData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(gameData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutDownExecutor() {
        serverGameExecutorService.shutdownNow();
        serverGameExecutorService = null;
    }

    /**
     * Creates a task that updates the mod's new time every day.
     */
    private void elapseDayTimer() {
        serverGameExecutorService.schedule(() -> {
            GameDateEvents.DayHasElapsedEvent.EVENT.invoker().changeDay(day());
            elapseDayTimer();
        }, 1 + ChronoUnit.MINUTES.between(LocalDateTime.now(), LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1)), TimeUnit.MINUTES);
    }

    /**
     * Creates a task that saves all the mod's game data every {@param rate}.
     * @param rate of time which the function will be executed.
     */
    private void autoSaveTimer(int rate) {
        serverGameExecutorService.scheduleAtFixedRate(() -> {
            theAbyssServer.dataManager().save();
            TheAbyss.getLogger().info("The mod's game data has been automatically saved.");
        }, rate, rate, TimeUnit.MINUTES);
    }

    /**
     * @return the server manager.
     */
    public TheAbyssServer serverManager() {
        return theAbyssServer;
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
     * @return the TotemManager object.
     */
    public TotemManager totemManager() {
        return totemManager;
    }

    /**
     * @return the Flash Bang Manager object.
     */
    public FlashBangServerManager flashBangManager() {
        return flashBangManager;
    }

    /**
     * @return the day of the Game.
     */
    public long day() {
        return gameData.day();
    }

}
