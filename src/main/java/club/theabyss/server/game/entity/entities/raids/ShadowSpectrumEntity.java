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

public class ShadowSpectrumEntity extends ElementalSpectrumEntity {

    public ShadowSpectrumEntity(EntityType<? extends ShadowSpectrumEntity> entityType, World world) {
        super(entityType, world);

        this.setCustomName(ChatFormatter.stringFormatToText("&8Shadow Spectrum"));
    }

    @Override
    public boolean tryAttack(Entity entity) {
        if (super.tryAttack(entity)) {
            if (entity.getType().downcast(entity) instanceof LivingEntity livingEntity) {
                livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 20 * 5, 0));
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
