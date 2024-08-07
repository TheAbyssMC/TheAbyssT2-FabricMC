package club.theabyss.client.render.renders.entities.spiders;

import club.theabyss.client.render.models.entities.spiders.WeaverSpiderModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

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

public class WeaverSpiderEntityRenderer extends GeoEntityRenderer<WeaverSpiderEntity> {

    public WeaverSpiderEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new WeaverSpiderModel());
    }

    @Override
    public Identifier getTextureLocation(WeaverSpiderEntity instance) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/spiders/weaver.png");
    }

}
