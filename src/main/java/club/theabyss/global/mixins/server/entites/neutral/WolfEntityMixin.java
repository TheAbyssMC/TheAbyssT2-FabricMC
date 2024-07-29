package club.theabyss.global.mixins.server.entites.neutral;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

@Mixin(WolfEntity.class)
public class WolfEntityMixin extends MobEntity {

    protected WolfEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() >=7) addStatusEffect(new StatusEffectInstance(StatusEffects.BAD_OMEN, Integer.MAX_VALUE, 4));
    }

    @Inject(at = @At("RETURN"), method = "createWolfAttributes")
    private static void addAttackDamageAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    @Inject(at = @At("HEAD"), method = "initGoals")
    public void addAttackGoal(CallbackInfo ci) {
        var day = GlobalDataAccess.getNowDay();
        if (day >= 7) {
            targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
            goalSelector.add(0, new MeleeAttackGoal(((WolfEntity) (Object) this), 1.0D, false));
        }
    }

}
