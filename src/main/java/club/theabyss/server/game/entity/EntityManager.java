package club.theabyss.server.game.entity;

import club.theabyss.global.interfaces.server.entity.IMobEntity;
import club.theabyss.global.interfaces.server.entity.skeleton.IAbstractSkeletonEntity;
import club.theabyss.server.game.ServerGameManager;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;

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

public class EntityManager {

    private static ServerGameManager serverGameManager;

    public EntityManager(final ServerGameManager serverGameManager) {
        EntityManager.serverGameManager = serverGameManager;
    }

    public static void reloadEntities() {
        var worlds = serverGameManager.minecraftServer().getWorlds();

        worlds.forEach(world -> world.iterateEntities().forEach(entity -> {
            if (entity instanceof MobEntity mobEntity) {
                var mob = ((IMobEntity)mobEntity);
                mob.reloadGoals();
                if (entity instanceof AbstractSkeletonEntity skeletonEntity) {
                    skeletonEntity.updateAttackType();
                    ((IAbstractSkeletonEntity)skeletonEntity).initEquipment$0(skeletonEntity.getWorld().getLocalDifficulty(skeletonEntity.getBlockPos()));
                }
                mob.reloadDataTracker();
            }
        }));
    }

}
