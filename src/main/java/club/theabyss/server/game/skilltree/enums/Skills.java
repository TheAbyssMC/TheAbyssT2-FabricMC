package club.theabyss.server.game.skilltree.enums;

import club.theabyss.TheAbyss;
import club.theabyss.server.game.skilltree.data.SkillTreeData;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.List;

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

public enum Skills {

    Health(1),
    Resistance(1),
    Strength(1);

    public final int day;
    public final List<String> lore;

    Skills(int day, List<String> lore) {
        this.day = day;
        this.lore = lore;
    }

    Skills(int day) {
        this(day, new ArrayList<>());
    }

    public long getPrice(ServerPlayerEntity player) {
        return getPrice(getLevel(player));
    }

    public static long getPrice(long level) {
        return (level * 2) + 1;
    }

    public long getLevel(ServerPlayerEntity player) {
        var uuid = player.getUuid();
        var playerSkills = skillData().getPlayerSkills();

        return (playerSkills.containsKey(uuid) && playerSkills.get(uuid).containsKey(this.toString())) ? playerSkills.get(player.getUuid()).get(this.toString()) : 0;
    }

    public boolean isAvailable() {
        return TheAbyss.getInstance().serverGameManager().day() >= day;
    }

    public static SkillTreeData skillData() {
        return TheAbyss.getInstance().serverManager().skillTreeManager().skillTreeData();
    }

}
