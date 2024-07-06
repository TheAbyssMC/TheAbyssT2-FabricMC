package club.theabyss.server.game.entity.entities.raids;

import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;

public class FireSpectrumEntity extends ElementalSpectrumEntity {

    public FireSpectrumEntity(EntityType<? extends FireSpectrumEntity> entityType, World world) {
        super(entityType, world);
        this.setCustomName(ChatFormatter.stringFormatToText("&cFire Spectrum"));
    }

    @Override
    protected void initEquipment(LocalDifficulty localDifficulty) {
        var sword = new ItemStack(Items.NETHERITE_SWORD);

        sword.addEnchantment(Enchantments.FIRE_ASPECT, 2);

        this.equipStack(EquipmentSlot.MAINHAND, sword);
        this.setEquipmentDropChance(EquipmentSlot.MAINHAND, 0.0F);
    }

    @Override
    protected void updateEnchantments(LocalDifficulty localDifficulty) {
    }

}
