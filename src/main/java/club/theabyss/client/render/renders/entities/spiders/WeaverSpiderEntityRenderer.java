package club.theabyss.client.render.renders.entities.spiders;

import club.theabyss.client.render.models.entities.spiders.WeaverSpiderModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class WeaverSpiderEntityRenderer extends GeoEntityRenderer<WeaverSpiderEntity> {

    public WeaverSpiderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WeaverSpiderModel());
    }

    @Override
    public Identifier getTextureLocation(WeaverSpiderEntity instance) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/spiders/weaver.png");
    }

}
