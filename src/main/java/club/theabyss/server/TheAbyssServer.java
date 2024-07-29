package club.theabyss.server;

import club.theabyss.TheAbyss;
import club.theabyss.server.data.DataManager;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.deathmessages.DeathMessagesManager;
import club.theabyss.server.game.skilltree.SkillTreeManager;
import club.theabyss.server.global.utils.customGlyphs.Animation;
import club.theabyss.server.global.utils.customGlyphs.NoFramesException;
import club.theabyss.server.networking.receivers.RegisterServerReceivers;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

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

public class TheAbyssServer {

    private final TheAbyss theAbyss;

    private DataManager dataManager;
    private ServerGameManager serverGameManager;
    private DeathMessagesManager deathMessagesManager;
    private SkillTreeManager skillTreeManager;

    private MinecraftServer minecraftServer;

    public TheAbyssServer(final TheAbyss theAbyss) {
        this.theAbyss = theAbyss;

        ServerLifecycleEvents.SERVER_STARTED.register((server -> {
            this.minecraftServer = server;

            try {
                this.dataManager = new DataManager(theAbyss, server);
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

            RegisterServerReceivers.init();

            TheAbyss.getLogger().info("The mod's server has been loaded successfully.");
        }));
        ServerLifecycleEvents.SERVER_STOPPED.register((server -> {
            try {
                dataManager.save();
            } catch (Exception e) {
                e.printStackTrace();
            }

            var bloodMoonManager = serverGameManager.bloodMoonManager();

            bloodMoonManager.cancelBossBarTask(server);

            serverGameManager.shutDownExecutor();

            TheAbyss.getLogger().info("The mod's server has been unloaded successfully.");
        }));
    }

    /**
     * @return the mod's main class.
     */
    public TheAbyss theAbyss() {
        return theAbyss;
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
