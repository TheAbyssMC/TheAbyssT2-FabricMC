package club.theabyss.global.mixins.server.entites.hostile.skeleton;

import club.theabyss.global.interfaces.server.entity.skeleton.IAbstractSkeletonEntity;
import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Random;

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

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin implements IAbstractSkeletonEntity {

    @Shadow protected abstract void initEquipment(LocalDifficulty difficulty);

    @ModifyArg(method = "initEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/AbstractSkeletonEntity;equipStack(Lnet/minecraft/entity/EquipmentSlot;Lnet/minecraft/item/ItemStack;)V"), index = 1)
    private ItemStack addPoweredBow(ItemStack stack) {
        var random = new Random();
        var day = GlobalDataAccess.getNowDay();

        if (day >= 7 && day < 14) {
            stack.addEnchantment(Enchantments.POWER, random.nextInt(10, 30));
        }

        return stack;
    }

    @Override
    public void initEquipment$0(LocalDifficulty localDifficulty) {
        initEquipment(localDifficulty);
    }
}
