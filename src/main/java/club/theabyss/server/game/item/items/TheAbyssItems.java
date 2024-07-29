package club.theabyss.server.game.item.items;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.groups.TheAbyssItemGroups;
import club.theabyss.server.game.item.items.twilight.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

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

public class TheAbyssItems {

    public static Item TWILIGHT_SWORD;
    public static Item TWILIGHT_PICKAXE;
    public static Item TWILIGHT_AXE;
    public static Item TWILIGHT_SHOVEL;
    public static Item TWILIGHT_HOE;
    public static Item TWILIGHT_BOW;
    public static Item TWILIGHT_TRIDENT;

    public static void registerModItems() {
        TWILIGHT_SWORD = registerItem("twilight_sword", new TwilightSwordItem(4, -2.4f, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TWILIGHT_PICKAXE = registerItem("twilight_pickaxe", new TwilightPickaxeItem(1, -2.8F, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TWILIGHT_AXE = registerItem("twilight_axe", new TwilightAxeItem(7, -3, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TWILIGHT_SHOVEL = registerItem("twilight_shovel", new TwilightShovelItem(1.5f, -3f, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TWILIGHT_HOE = registerItem("twilight_hoe", new TwilightHoeItem(-4, 0, new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof()));
        TWILIGHT_BOW = registerItem("twilight_bow", new TwilightBowItem(new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof().maxCount(1)));
        TWILIGHT_TRIDENT = registerItem("twilight_trident", new TwilightTridentItem(new FabricItemSettings().group(TheAbyssItemGroups.TWILIGHT_ITEMS).fireproof().maxCount(1)));
        TheAbyss.getLogger().info("The mod items have been registered successfully.");
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(TheAbyssConstants.MOD_ID, name), item);
    }

}
