package club.theabyss.client.render.renders;

import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import club.theabyss.client.render.models.spiders.WeaverSpiderModel;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class WeaverSpiderEntityRenderer extends MobEntityRenderer<WeaverSpiderEntity, WeaverSpiderModel> {

    public WeaverSpiderEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WeaverSpiderModel(context.getPart(TheAbyssModelLayers.MODEL_WEAVER_LAYER)), 0.5f);
    }

    @Override
    public Identifier getTexture(WeaverSpiderEntity entity) {
        return new Identifier("theabyss2", "textures/entities/spiders/weaver.png");
    }

}
