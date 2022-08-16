package club.theabyss.client.render.models.entities.spiders;

import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class BlackWidowSpiderModel extends AnimatedGeoModel<BlackWidowSpiderEntity> {

    @Override
    public Identifier getModelLocation(BlackWidowSpiderEntity object) {
        return new Identifier("theabyss2", "geo/models/spiders/black_widow_model.geo.json");
    }

    @Override
    public Identifier getTextureLocation(BlackWidowSpiderEntity object) {
        return new Identifier("theabyss2", "entities/textures/spiders/black_widow");
    }

    @Override
    public Identifier getAnimationFileLocation(BlackWidowSpiderEntity animatable) {
        return new Identifier("theabyss2", "animations/spiders/black_widow.animation.json");
    }

}
