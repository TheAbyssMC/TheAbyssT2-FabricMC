package club.theabyss.server.game.skilltree;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.server.TheAbyssServer;
import club.theabyss.server.game.skilltree.data.SkillTreeData;
import club.theabyss.server.game.skilltree.enums.Skills;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Objects;

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

public class SkillTreeManager implements Restorable {

    private final TheAbyssServer theAbyssServer;

    private SkillTreeData skillTreeData;

    public SkillTreeManager(final TheAbyssServer theAbyssServer) {
        this.theAbyssServer = theAbyssServer;

        restore(theAbyssServer.dataManager().skillTree());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.skillTreeData = new SkillTreeData();
        } else {
            this.skillTreeData = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), SkillTreeData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(skillTreeData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public long getSkillLevel(ServerPlayerEntity player, Skills skills) {
        return skills.getLevel(player);
    }

    public static void updatePlayerHealth(ServerPlayerEntity player) {
        var skillTreeManager = TheAbyss.getInstance().serverManager().skillTreeManager();

        Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).setBaseValue(skillTreeManager.getSkillLevel(player, Skills.Health) * 2 + 20);
    }

    /**
     * @return the skill tree data.
     */
    public SkillTreeData skillTreeData() {
        return skillTreeData;
    }

    /**
     * @return the server manager.
     */
    public TheAbyssServer theAbyssServer() {
        return theAbyssServer;
    }

}
