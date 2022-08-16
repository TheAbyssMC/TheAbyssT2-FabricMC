package club.theabyss.client.render;

import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.client.render.models.entities.fox.ThiefFoxModel;
import club.theabyss.client.render.models.entities.spiders.WeaverSpiderModel;
import club.theabyss.client.render.models.entities.watercreatures.KrakenEntityModel;
import club.theabyss.client.render.renders.entities.fox.ThiefFoxEntityRenderer;
import club.theabyss.client.render.renders.entities.spiders.BlackWidowSpiderEntityRenderer;
import club.theabyss.client.render.renders.entities.spiders.WeaverSpiderEntityRenderer;
import club.theabyss.client.render.renders.entities.watercreatures.KrakenEntityRenderer;
import club.theabyss.server.game.entity.entities.TheAbyssEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistries {

    public static void register() {
        EntityRendererRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.WEAVER_SPIDER_MODEL_LAYER, WeaverSpiderModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.THIEF_FOX, ThiefFoxEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.THIEF_FOX_MODEL_LAYER, ThiefFoxModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.KRAKEN, KrakenEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.KRAKEN_MODEL_LAYER, KrakenEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.BLACK_WIDOW_SPIDER, BlackWidowSpiderEntityRenderer::new);
    }

}
