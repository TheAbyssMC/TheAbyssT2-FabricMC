package club.theabyss.client;

import club.theabyss.client.global.listeners.GlobalClientListeners;
import club.theabyss.client.networking.receivers.RegisterClientReceivers;
import club.theabyss.client.render.RenderRegistries;
import club.theabyss.client.render.renders.TheAbyssBuiltinItemModelRenderer;
import club.theabyss.server.game.item.items.TheAbyssItems;
import club.theabyss.server.global.utils.AbyssModelPredicateProvider;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceType;

public class TheAbyssClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegisterClientReceivers.init();
        GlobalClientListeners.init();
        RenderRegistries.register();
        AbyssModelPredicateProvider.registerAbyssModels();
    }

    public static void registerBuiltinItemRenderers(MinecraftClient client) {
        TheAbyssBuiltinItemModelRenderer builtinItemModelRenderer = new TheAbyssBuiltinItemModelRenderer(client.getEntityModelLoader());

        ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(builtinItemModelRenderer);

        BuiltinItemRendererRegistry.DynamicItemRenderer dynamicItemRenderer = builtinItemModelRenderer::render;

        BuiltinItemRendererRegistry.INSTANCE.register(TheAbyssItems.TWILIGHT_TRIDENT, dynamicItemRenderer);
    }

}
