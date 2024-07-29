package club.theabyss.global.mixins.server.entites.neutral.brain;

import club.theabyss.global.utils.GlobalDataAccess;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.entity.passive.GoatBrain;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

@Mixin(GoatBrain.class)
public class GoatBrainMixin {

    @Shadow @Final private static UniformIntProvider RAM_COOLDOWN_RANGE;
    @Shadow @Final private static TargetPredicate RAM_TARGET_PREDICATE;
    @Shadow @Final private static UniformIntProvider SCREAMING_RAM_COOLDOWN_RANGE;

    @Shadow @Final private static UniformIntProvider WALKING_SPEED;

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatBrain;addRamActivities(Lnet/minecraft/entity/ai/brain/Brain;)V"))
    private static void modifyRamActivities(Brain<GoatEntity> brain) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            TargetPredicate MODIFIED_RAM_TARGET_PREDICATE = TargetPredicate.createAttackable().setPredicate((livingEntity) -> livingEntity.getType().equals(EntityType.PLAYER));
            UniformIntProvider MODIFIED_RAM_COOLDOWN_RANGE = UniformIntProvider.create(50, 100);

            brain.setTaskList(Activity.RAM, ImmutableList.of(Pair.of(0, new RamImpactTask<>((goatEntity) -> {
                return MODIFIED_RAM_COOLDOWN_RANGE;
            }, MODIFIED_RAM_TARGET_PREDICATE, 3.0F, (goatEntity) -> 15.0D, (goatEntity) -> {
                return goatEntity.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_RAM_IMPACT : SoundEvents.ENTITY_GOAT_RAM_IMPACT;
            })), Pair.of(1, new PrepareRamTask<>((goatEntity) -> {
                return MODIFIED_RAM_COOLDOWN_RANGE.getMin();
            }, 1, 14, 1.25F, MODIFIED_RAM_TARGET_PREDICATE, 20, (goatEntity) -> {
                return goatEntity.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_PREPARE_RAM : SoundEvents.ENTITY_GOAT_PREPARE_RAM;
            }))), ImmutableSet.of(Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleState.VALUE_ABSENT)));
        } else {
            brain.setTaskList(Activity.RAM, ImmutableList.of(Pair.of(0, new RamImpactTask<>((goatEntity) -> {
                return goatEntity.isScreaming() ? SCREAMING_RAM_COOLDOWN_RANGE : RAM_COOLDOWN_RANGE;
            }, RAM_TARGET_PREDICATE, 3.0F, (goatEntity) -> {
                return goatEntity.isBaby() ? 1.0 : 2.5;
            }, (goatEntity) -> {
                return goatEntity.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_RAM_IMPACT : SoundEvents.ENTITY_GOAT_RAM_IMPACT;
            })), Pair.of(1, new PrepareRamTask<>((goatEntity) -> {
                return goatEntity.isScreaming() ? SCREAMING_RAM_COOLDOWN_RANGE.getMin() : RAM_COOLDOWN_RANGE.getMin();
            }, 4, 7, 1.25F, RAM_TARGET_PREDICATE, 20, (goatEntity) -> {
                return goatEntity.isScreaming() ? SoundEvents.ENTITY_GOAT_SCREAMING_PREPARE_RAM : SoundEvents.ENTITY_GOAT_PREPARE_RAM;
            }))), ImmutableSet.of(Pair.of(MemoryModuleType.TEMPTING_PLAYER, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.BREED_TARGET, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleState.VALUE_ABSENT)));
        }
    }

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatBrain;addCoreActivities(Lnet/minecraft/entity/ai/brain/Brain;)V"))
    private static void modifyCoreActivities(Brain<GoatEntity> brain) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            brain.setTaskList(Activity.CORE, 1, ImmutableList.of(new StayAboveWaterTask(0.8F), new WalkTask(4.0F), new LookAroundTask(45, 90), new WanderAroundTask(), new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new TemptationCooldownTask(MemoryModuleType.LONG_JUMP_COOLING_DOWN), new TemptationCooldownTask(MemoryModuleType.RAM_COOLDOWN_TICKS), new ForgetAngryAtTargetTask<>()));
        } else {
            brain.setTaskList(Activity.CORE, 1, ImmutableList.of(new StayAboveWaterTask(0.8F), new WalkTask(4.0F), new LookAroundTask(45, 90), new WanderAroundTask(), new TemptationCooldownTask(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS), new TemptationCooldownTask(MemoryModuleType.LONG_JUMP_COOLING_DOWN), new TemptationCooldownTask(MemoryModuleType.RAM_COOLDOWN_TICKS)));
        }
    }

    @Redirect(method = "create", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatBrain;addIdleActivities(Lnet/minecraft/entity/ai/brain/Brain;)V"))
    private static void modifyIdleActivities(Brain<GoatEntity> brain) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new UpdateAttackTargetTask<>(GoatBrainMixin::getTarget)) ,Pair.of(1, new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 6.0F), UniformIntProvider.create(30, 60))), Pair.of(1, new BreedTask(EntityType.GOAT, 1.0F)), Pair.of(2, new TemptTask((livingEntity) -> 1.25F)), Pair.of(3, new WalkTowardClosestAdultTask<>(WALKING_SPEED, 1.25F)), Pair.of(4, new RandomTask<>(ImmutableList.of(Pair.of(new StrollTask(1.0F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))), ImmutableSet.of(Pair.of(MemoryModuleType.RAM_TARGET, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT)));
        } else {
            brain.setTaskList(Activity.IDLE, ImmutableList.of(Pair.of(0, new TimeLimitedTask<>(new FollowMobTask(EntityType.PLAYER, 6.0F), UniformIntProvider.create(30, 60))), Pair.of(0, new BreedTask(EntityType.GOAT, 1.0F)), Pair.of(1, new TemptTask((livingEntity) -> 1.25F)), Pair.of(2, new WalkTowardClosestAdultTask<>(WALKING_SPEED, 1.25F)), Pair.of(3, new RandomTask<>(ImmutableList.of(Pair.of(new StrollTask(1.0F), 2), Pair.of(new GoTowardsLookTarget(1.0F, 3), 2), Pair.of(new WaitTask(30, 60), 1))))), ImmutableSet.of(Pair.of(MemoryModuleType.RAM_TARGET, MemoryModuleState.VALUE_ABSENT), Pair.of(MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleState.VALUE_ABSENT)));
        }
    }

    @Inject(at = @At("HEAD"), method = "updateActivities", cancellable = true)
    private static void modifyUpdateActivities(GoatEntity goatEntity, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            goatEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.RAM, Activity.LONG_JUMP, Activity.IDLE, Activity.FIGHT));
        } else {
            goatEntity.getBrain().resetPossibleActivities(ImmutableList.of(Activity.RAM, Activity.LONG_JUMP, Activity.IDLE));
        }
        ci.cancel();
    }

    @Unique
    private static Optional<? extends LivingEntity> getTarget(GoatEntity goatEntity) {
        Optional<LivingEntity> optional = LookTargetUtil.getEntity(goatEntity, MemoryModuleType.ANGRY_AT);
        if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(goatEntity, optional.get())) {
            return optional;
        } else {
            Optional<? extends LivingEntity> optional2 = findNearestVisibleTargetPlayer(goatEntity);
            return optional2.isPresent() ? optional2 : goatEntity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
        }
    }

    @Unique
    private static Optional<? extends LivingEntity> findNearestVisibleTargetPlayer(GoatEntity goatEntity) {
        return goatEntity.getBrain().getOptionalMemory((MemoryModuleType<? extends LivingEntity>) MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).filter((livingEntity) -> livingEntity.isInRange(goatEntity, 12.0));
    }

}
