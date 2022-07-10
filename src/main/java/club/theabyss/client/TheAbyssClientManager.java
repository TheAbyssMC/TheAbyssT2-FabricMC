package club.theabyss.client;

import club.theabyss.client.global.listeners.GlobalClientListeners;
import club.theabyss.client.render.RenderRegistries;
import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.client.render.models.spiders.WeaverSpiderModel;
import club.theabyss.client.render.renders.WeaverSpiderEntityRenderer;
import club.theabyss.server.game.entity.entities.TheAbyssEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class TheAbyssClientManager implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        GlobalClientListeners.init();
        RenderRegistries.register();
    }

}
