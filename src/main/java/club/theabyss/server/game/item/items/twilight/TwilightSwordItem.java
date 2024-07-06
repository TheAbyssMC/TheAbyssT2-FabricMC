package club.theabyss.server.game.item.items.twilight;

import club.theabyss.server.game.item.items.twilight.material.TwilightToolMaterial;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TwilightSwordItem extends SwordItem {

    public TwilightSwordItem(int baseDamage, float attackSpeed, Settings settings) {
        super(new TwilightToolMaterial(), baseDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack itemStack, LivingEntity livingEntity, LivingEntity livingEntity2) {
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, true, true));
        livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, true, true));

        return true;
    }

    @Override
    public Text getName() {
        return ChatFormatter.stringFormatToText("&6Twilight Sword");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return ChatFormatter.stringFormatToText("&6Twilight Sword");
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
        list.add(ChatFormatter.stringFormatToText("&7Todos los golpes dados con su filo tienen"));
        list.add(ChatFormatter.stringFormatToText("&7la capacidad de hacer brillar a cualquiera"));
        list.add(ChatFormatter.stringFormatToText("&7que se cruce en su camino, dejándolo"));
        list.add(ChatFormatter.stringFormatToText("&7inmóvil por un corto período de tiempo."));
        list.add(ChatFormatter.stringFormatToText(""));
    }
}
