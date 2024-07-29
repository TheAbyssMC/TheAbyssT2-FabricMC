package club.theabyss.server.game.entity.entities.zombie;

import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

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

public class ZombieZEntity extends ZombieEntity {

    public ZombieZEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        setCustomName(ChatFormatter.stringFormatToText("&5Zombie Z"));

        if (GlobalDataAccess.getNowDay() < 14) {
            setHealth(30);
        } else {
            setHealth(40);
        }
    }

    public static DefaultAttributeContainer.Builder createZombieZAttributes() {
        var attributes = HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);

        if (GlobalDataAccess.getNowDay() < 14) {
            attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12);
            attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 30);
        } else {
            attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 16);
            attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 40);
        }

        return attributes;
    }

}
