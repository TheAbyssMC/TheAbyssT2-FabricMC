package club.theabyss.client.render;

import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.client.render.models.spiders.WeaverSpiderModel;
import club.theabyss.client.render.renders.WeaverSpiderEntityRenderer;
import club.theabyss.server.game.entity.entities.TheAbyssEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

public class RenderRegistries {

    public static void register() {
        EntityRendererRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.MODEL_WEAVER_LAYER, WeaverSpiderModel::getTexturedModelData);
    }

}
