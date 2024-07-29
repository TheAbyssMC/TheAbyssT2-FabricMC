package club.theabyss.server.game.deathmessages;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.TheAbyssServer;
import club.theabyss.server.data.storage.DeathMessages;

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

public class DeathMessagesManager implements Restorable {

    private final TheAbyssServer theAbyssServer;
    private DeathMessages deathMessages;

    public DeathMessagesManager(final TheAbyssServer theAbyssServer) {
        this.theAbyssServer = theAbyssServer;

        this.restore(theAbyssServer.dataManager().deathMessages());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.deathMessages = new DeathMessages();
        } else {
            this.deathMessages = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), DeathMessages.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(deathMessages).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return a list of the death messages.
     */
    public DeathMessages deathMessages() {
        return deathMessages;
    }

    /**
     * @return the mod's server manager.
     */
    public TheAbyssServer theAbyssServer() {
        return theAbyssServer;
    }

}
