package club.theabyss.server.game.entity.entities.goat;

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.ForgetAttackTargetTask;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MeleeAttackTask;
import net.minecraft.entity.ai.brain.task.RangedApproachTask;
import net.minecraft.entity.passive.GoatBrain;
import net.minecraft.entity.passive.GoatEntity;

import java.util.Optional;

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

public class CustomGoatBrain extends GoatBrain {

    public static Brain<?> create(GoatEntity goatEntity, Brain<GoatEntity> brain) {
        addFightActivities(goatEntity, brain);
        return create(brain);
    }

    private static boolean isTarget(GoatEntity goatEntity, LivingEntity livingEntity) {
        return getTarget(goatEntity).filter((livingEntity2) -> livingEntity2 == livingEntity).isPresent();
    }

    private static void addFightActivities(GoatEntity goatEntity, Brain<GoatEntity> brain) {
        brain.setTaskList(Activity.FIGHT, 10, ImmutableList.of(new ForgetAttackTargetTask<>((livingEntity) -> !isTarget(goatEntity, livingEntity)), new RangedApproachTask(2.25F), new MeleeAttackTask(40)), MemoryModuleType.ATTACK_TARGET);
    }

    private static Optional<? extends LivingEntity> getTarget(GoatEntity goatEntity) {
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(goatEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(goatEntity, optional.get())) {
            return optional;
        } else {
            Optional<? extends LivingEntity> optional2 = findNearestVisibleTargetPlayer(goatEntity);
            return optional2.isPresent() ? optional2 : goatEntity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        }
    }

    private static Optional<? extends LivingEntity> findNearestVisibleTargetPlayer(GoatEntity goatEntity) {
        return goatEntity.getBrain().getOptionalMemory((MemoryModuleType<? extends LivingEntity>) MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).filter((livingEntity) -> livingEntity.isInRange(goatEntity, 12.0));
    }

}
