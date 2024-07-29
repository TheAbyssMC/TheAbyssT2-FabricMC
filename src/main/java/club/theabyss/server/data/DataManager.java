package club.theabyss.server.data;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Instantiable;
import club.theabyss.server.TheAbyssServer;
import net.minecraft.server.MinecraftServer;

import java.io.File;

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

public class DataManager extends Instantiable<TheAbyssServer> {

    private final JsonConfig gameData;
    private final JsonConfig deathMessages;
    private final JsonConfig skillTree;
    private final JsonConfig totems;
    private final JsonConfig flashBangData;

    public DataManager(final TheAbyss core, MinecraftServer server) throws Exception {
        super(core.serverManager());

        final String folderName = "theabyssmanager";

        this.gameData = (server.isDedicated()) ? JsonConfig.serverConfig("gameData.json", folderName, server) : JsonConfig.savesConfig("gameDataConfig.json", folderName, server);
        this.deathMessages = (server.isDedicated()) ? JsonConfig.serverConfig("deathMessages.json", folderName, server) : JsonConfig.savesConfig("deathMessages.json", folderName, server);
        this.skillTree = (server.isDedicated()) ? JsonConfig.serverConfig("skillTree.json", folderName, server) : JsonConfig.savesConfig("skillTree.json", folderName, server);
        this.totems = (server.isDedicated()) ? JsonConfig.serverConfig("totems.json", folderName, server) : JsonConfig.savesConfig("totems.json", folderName, server);
        this.flashBangData = (server.isDedicated()) ? JsonConfig.serverConfig( "flashBang" + File.separatorChar + "flashBangData.json", folderName, server) : JsonConfig.savesConfig("flashBang" + File.separatorChar + "flashBangData.json", folderName, server);
    }

    /**
     * A method that saves the current state of the application to json files.
     */
    public void save() {
        instance().serverGameManager().save(gameData);
        instance().deathMessagesManager().save(deathMessages);
        instance().skillTreeManager().save(skillTree);
        instance().serverGameManager().totemManager().save(totems);
        instance().serverGameManager().flashBangManager().save(flashBangData);
    }

    /**
     * @return the game data.
     */
    public JsonConfig gameData() {
        return this.gameData;
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

    /**
     * @return the used totems.
     */
    public JsonConfig totems() {
        return this.totems;
    }

    /**
     * @return the flash bang data.
     */
    public JsonConfig flashBangData() {
        return this.flashBangData;
    }

}
