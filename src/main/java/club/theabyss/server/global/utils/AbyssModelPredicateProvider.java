package club.theabyss.server.global.utils;

import club.theabyss.TheAbyss;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class AbyssModelPredicateProvider {

    public static void registerAbyssModels() {
        registerBowModels();
        registerTridentModels();
        TheAbyss.getLogger().info("The model predicates have been registered successfully.");
    }

    private static void registerBowModels() {
        registerBow(TheAbyssItems.TWILIGHT_BOW);
    }

    private static void registerBow(Item bow) {
        ModelPredicateProviderRegistry.register(bow, new Identifier("pull"), (stack, world, entity, seed) -> {
            if (entity == null) {
                return 0.0f;
            }
            if (entity.getActiveItem() != stack) {
                return 0.0f;
            }

            return (float)(stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 20.0f;
        });

        ModelPredicateProviderRegistry.register(bow, new Identifier("pulling"),
                (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0f : 0.0f);
    }

    private static void registerTridentModels() {
        registerTrident(TheAbyssItems.TWILIGHT_TRIDENT);
    }

    private static void registerTrident(Item trident) {
        ModelPredicateProviderRegistry.register(trident, new Identifier("throwing"), (itemStack, clientWorld, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getActiveItem() == itemStack ? 1.0F : 0.0F);
    }

}
