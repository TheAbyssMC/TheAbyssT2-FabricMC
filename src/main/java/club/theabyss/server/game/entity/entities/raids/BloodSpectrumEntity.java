package club.theabyss.server.game.entity.entities.raids;

import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.LocalDifficulty;
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

public class BloodSpectrumEntity extends ElementalSpectrumEntity {

    public BloodSpectrumEntity(EntityType<? extends BloodSpectrumEntity> entityType, World world) {
        super(entityType, world);

        this.setCustomName(ChatFormatter.stringFormatToText("&cBlood Spectrum"));
    }

    @Override
    public boolean tryAttack(Entity entity) {
        if (super.tryAttack(entity)) {
            if (entity.getType().downcast(entity) instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 20 * 5, 4));
            }
            return true;
        }
        return false;
    }

    @Override
    protected void initEquipment(LocalDifficulty localDifficulty) {
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.NETHERITE_SWORD));
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected void updateEnchantments(LocalDifficulty localDifficulty) {
    }
}
