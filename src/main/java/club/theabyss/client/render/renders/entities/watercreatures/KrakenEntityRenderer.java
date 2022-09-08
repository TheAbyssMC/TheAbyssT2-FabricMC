package club.theabyss.client.render.renders.entities.watercreatures;

import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.client.render.models.entities.watercreatures.KrakenEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class KrakenEntityRenderer extends MobEntityRenderer<KrakenEntity, KrakenEntityModel> {

    public KrakenEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new KrakenEntityModel(context.getPart(TheAbyssModelLayers.KRAKEN_MODEL_LAYER)), 0.6f);
    }

    @Override
    public Identifier getTexture(KrakenEntity entity) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/watercreatures/kraken.png");
    }

}
