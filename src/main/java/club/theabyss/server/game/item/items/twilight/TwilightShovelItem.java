package club.theabyss.server.game.item.items.twilight;

import club.theabyss.server.game.item.items.twilight.material.TwilightToolMaterial;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TwilightShovelItem extends ShovelItem {

    public TwilightShovelItem(float f, float g, Settings settings) {
        super(new TwilightToolMaterial(), f, g, settings);
    }

    @Override
    public Text getName() {
        return ChatFormatter.stringFormatToText("&6Twilight Shovel");
    }

    @Override
    public Text getName(ItemStack itemStack) {
        return ChatFormatter.stringFormatToText("&6Twilight Shovel");
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
        list.add(ChatFormatter.stringFormatToText("&6&lHABILIDAD: &6Twilight Haste"));
        list.add(ChatFormatter.stringFormatToText("&7Esta habilidad permite romper bloques"));
        list.add(ChatFormatter.stringFormatToText("&7de forma mucho más rápida y eficiente."));
    }

}
