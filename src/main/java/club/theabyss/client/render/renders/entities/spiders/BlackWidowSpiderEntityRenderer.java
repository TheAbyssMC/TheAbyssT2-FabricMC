package club.theabyss.client.render.renders.entities.spiders;

import club.theabyss.client.render.models.entities.spiders.BlackWidowSpiderModel;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class BlackWidowSpiderEntityRenderer extends GeoEntityRenderer<BlackWidowSpiderEntity> {

    public BlackWidowSpiderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlackWidowSpiderModel());
    }

    @Override
    public Identifier getTextureLocation(BlackWidowSpiderEntity instance) {
        return new Identifier("theabyss2", "entities/textures/spiders/black_widow.png");
    }

}
