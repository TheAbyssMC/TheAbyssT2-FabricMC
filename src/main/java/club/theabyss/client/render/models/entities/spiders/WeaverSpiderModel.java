package club.theabyss.client.render.models.entities.spiders;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
