package club.theabyss.server.game.entity.entities.raids;

import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
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

public class WindSpectrumEntity extends ElementalSpectrumEntity {

    public WindSpectrumEntity(EntityType<? extends WindSpectrumEntity> entityType, World world) {
        super(entityType, world);

        this.setCustomName(ChatFormatter.stringFormatToText("&fWind Spectrum"));
    }

    @Override
    protected void initEquipment(LocalDifficulty localDifficulty) {
        var sword = new ItemStack(Items.NETHERITE_SWORD);

        sword.addEnchantment(Enchantments.KNOCKBACK, 2);

        this.equipStack(EquipmentSlot.MAINHAND, sword);
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected void updateEnchantments(LocalDifficulty localDifficulty) {
    }
}
