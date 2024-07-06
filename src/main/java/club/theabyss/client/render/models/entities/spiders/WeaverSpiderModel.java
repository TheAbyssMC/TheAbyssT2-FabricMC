package club.theabyss.client.render.models.entities.spiders;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WeaverSpiderModel extends AnimatedGeoModel<WeaverSpiderEntity> {

    @Override
    public Identifier getModelLocation(WeaverSpiderEntity object) {
        return new Identifier(TheAbyssConstants.MOD_ID, "geo/models/spiders/weaver_model.geo.json");
    }

    @Override
    public Identifier getTextureLocation(WeaverSpiderEntity object) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/spiders/weaver.png");
    }

    @Override
    public Identifier getAnimationFileLocation(WeaverSpiderEntity animatable) {
        return new Identifier(TheAbyssConstants.MOD_ID, "animations/spiders/weaver.animation.json");
    }

}
