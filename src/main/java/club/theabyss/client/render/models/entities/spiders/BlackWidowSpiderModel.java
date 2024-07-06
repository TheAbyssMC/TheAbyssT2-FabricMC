package club.theabyss.client.render.models.entities.spiders;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlackWidowSpiderModel extends AnimatedGeoModel<BlackWidowSpiderEntity> {

    @Override
    public Identifier getModelLocation(BlackWidowSpiderEntity object) {
        return new Identifier(TheAbyssConstants.MOD_ID, "geo/models/spiders/black_widow_model.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BlackWidowSpiderEntity object) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/spiders/black_widow");
    }

    @Override
    public Identifier getAnimationFileLocation(BlackWidowSpiderEntity animatable) {
        return new Identifier(TheAbyssConstants.MOD_ID, "animations/spiders/black_widow.animation.json");
    }

}
