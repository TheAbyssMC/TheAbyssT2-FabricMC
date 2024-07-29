package club.theabyss.server.game.totem;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.totem.data.TotemData;

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

public class TotemManager implements Restorable {

    public final ServerGameManager gameManager;

    public TotemData totemData;


    public TotemManager(final ServerGameManager gameManager) {
        this.gameManager = gameManager;

        this.restore(gameManager.serverManager().dataManager().totems());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.totemData = new TotemData();
        } else {
            this.totemData = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), TotemData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(totemData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @return the mod's totem data class instance.
     */
    public TotemData totemData() {
        return totemData;
    }

}
