package club.theabyss.server.game.item.items.twilight;

import club.theabyss.global.interfaces.server.entity.projectile.IArrowEntity;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ArrowItem;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

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

public class TwilightBowItem extends BowItem {

    public TwilightBowItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean postHit(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, true, true));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, true, true));

        return true;
    }

    @Override
    public void onStoppedUsing(ItemStack itemStack, World world, LivingEntity livingEntity, int i) {
        if (livingEntity instanceof PlayerEntity playerEntity) {
            boolean bl = playerEntity.getAbilities().creativeMode || EnchantmentHelper.getLevel(Enchantments.INFINITY, itemStack) > 0;
            ItemStack itemStack2 = playerEntity.getArrowType(itemStack);
            if (!itemStack2.isEmpty() || bl) {
                if (itemStack2.isEmpty()) {
                    itemStack2 = new ItemStack(Items.ARROW);
                }

                int j = this.getMaxUseTime(itemStack) - i;
                float f = getPullProgress(j);
                if (!((double)f < 0.1)) {
                    boolean bl2 = bl && itemStack2.isOf(Items.ARROW);
                    if (!world.isClient) {
                        ArrowItem arrowItem = (ArrowItem)(itemStack2.getItem() instanceof ArrowItem ? itemStack2.getItem() : Items.ARROW);
                        PersistentProjectileEntity persistentProjectileEntity = arrowItem.createArrow(world, itemStack2, playerEntity);
                        persistentProjectileEntity.setVelocity(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, f * 3.0F, 1.0F);
                        if (f == 1.0F) {
                            persistentProjectileEntity.setCritical(true);
                        }

                        persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() * 3);

                        int k = EnchantmentHelper.getLevel(Enchantments.POWER, itemStack);
                        if (k > 0) {
                            persistentProjectileEntity.setDamage(persistentProjectileEntity.getDamage() + (double)k * 0.5 + 0.5);
                        }

                        int l = EnchantmentHelper.getLevel(Enchantments.PUNCH, itemStack);
                        if (l > 0) {
                            persistentProjectileEntity.setPunch(l);
                        }

                        if (EnchantmentHelper.getLevel(Enchantments.FLAME, itemStack) > 0) {
                            persistentProjectileEntity.setOnFireFor(100);
                        }

                        itemStack.damage(1, playerEntity, (playerEntity2) -> {
                            playerEntity2.sendToolBreakStatus(playerEntity.getActiveHand());
                        });
                        if (bl2 || playerEntity.getAbilities().creativeMode && (itemStack2.isOf(Items.SPECTRAL_ARROW) || itemStack2.isOf(Items.TIPPED_ARROW))) {
                            persistentProjectileEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                        }

                        NbtCompound nbtCompound = new NbtCompound();
                        nbtCompound.putBoolean("shotFromTwilightBow", true);

                        persistentProjectileEntity.writeNbt(nbtCompound);

                        ((IArrowEntity)persistentProjectileEntity).setShotFromTwilight$0(true);

                        world.spawnEntity(persistentProjectileEntity);
                    }

                    world.playSound(null, playerEntity.getX(), playerEntity.getY(), playerEntity.getZ(), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 1.2F) + f * 0.5F);
                    if (!bl2 && !playerEntity.getAbilities().creativeMode) {
                        itemStack2.decrement(1);
                        if (itemStack2.isEmpty()) {
                            playerEntity.getInventory().removeOne(itemStack2);
                        }
                    }

                    playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
                }
            }
        }
    }

    @Override
    public Text getName() {
        return ChatFormatter.stringFormatToText("&6Twilight Bow");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return ChatFormatter.stringFormatToText("&6Twilight Bow");
    }

    @Override
    public void postProcessNbt(NbtCompound nbtCompound) {
        super.postProcessNbt(nbtCompound);
        nbtCompound.putBoolean("Unbreakable", true);
    }

    @Override
    public void appendTooltip(ItemStack itemStack, @Nullable World world, List<Text> list, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack, world, list, tooltipContext);

        list.add(ChatFormatter.stringFormatToText(""));
        list.add(ChatFormatter.stringFormatToText("&7Fabricado con restos del crepúsculo."));
        list.add(ChatFormatter.stringFormatToText(""));
        list.add(ChatFormatter.stringFormatToText("&6&lHABILIDAD: &6Twilight Shine"));
        list.add(ChatFormatter.stringFormatToText("&7Todos sus proyectiles tienen la capacidad"));
        list.add(ChatFormatter.stringFormatToText("&7de hacer brillar a cualquiera que se cruce"));
        list.add(ChatFormatter.stringFormatToText("&7en su camino, dejándolo inmóvil por un corto "));
        list.add(ChatFormatter.stringFormatToText("&7período de tiempo."));
        list.add(ChatFormatter.stringFormatToText(""));
    }

}
