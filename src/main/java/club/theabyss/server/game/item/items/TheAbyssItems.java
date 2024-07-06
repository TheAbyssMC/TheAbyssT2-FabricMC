package club.theabyss.server.game.item.items;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.groups.TheAbyssItemGroups;
import club.theabyss.server.game.item.items.twilight.TwilightSwordItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheAbyssItems {

    public static Item TWILIGHT_SWORD;

    public static void registerModItems() {
        TWILIGHT_SWORD = registerItem("twilight_sword", new TwilightSwordItem(4, -2.4f, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TheAbyss.getLogger().info("The mod items have been registered successfully.");
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TheAbyssConstants.MOD_ID, name), item);
    }

}
