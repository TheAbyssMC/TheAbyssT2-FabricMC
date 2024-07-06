package club.theabyss.server.game.item.groups;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class TheAbyssItemGroups {

    public static ItemGroup TWILIGHT_ITEMS;

    public static void registerItemGroups() {
        TWILIGHT_ITEMS = FabricItemGroupBuilder.build(new Identifier(TheAbyssConstants.MOD_ID, "twilight"),
                () -> new ItemStack(TheAbyssItems.TWILIGHT_SWORD));
        TheAbyss.getLogger().info("The mod item groups have been registered successfully");
    }

}
