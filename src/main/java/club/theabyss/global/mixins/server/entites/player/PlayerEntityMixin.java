package club.theabyss.global.mixins.server.entites.player;

import club.theabyss.TheAbyss;
import club.theabyss.server.game.skilltree.enums.Skills;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    private float modifyAmount(float amount) {
        var serverManager = TheAbyss.getInstance().serverManager();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Resistance);

            if (level >= 1) {
                return (float) (amount * (1 - (level * 0.025)));
            }
        }
        return amount;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float modifyAttackDamageForE(float amount) {
        var serverManager = TheAbyss.getInstance().serverManager();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength);

            if (level >= 1) {
                return (float) (amount * (1+(skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength) * 0.025)));
            }
        }
        return amount;
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    public float modifyAttackDamageForLE(float amount) {
        var serverManager = TheAbyss.getInstance().serverManager();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength);

            if (level >= 1) {
                var damage = (float) (amount * (1+(skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength) * 0.025)));
                return 1.0F + (EnchantmentHelper.getSweepingMultiplier(((PlayerEntity)(Object) this)) * damage);
            }
        }
        return amount;
    }
}
