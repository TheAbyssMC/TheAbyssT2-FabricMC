package club.theabyss.client.render.renders.entities.zombie;

import club.theabyss.client.render.models.entities.zombie.ZombieZEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.zombie.ZombieZEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ZombieBaseEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

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

public class ZombieZEntityRenderer extends ZombieBaseEntityRenderer<ZombieZEntity, ZombieZEntityModel<ZombieZEntity>> {

    public ZombieZEntityRenderer(EntityRendererFactory.Context context) {
        this(context, EntityModelLayers.ZOMBIE, EntityModelLayers.ZOMBIE_INNER_ARMOR, EntityModelLayers.ZOMBIE_OUTER_ARMOR);
    }

    public ZombieZEntityRenderer(EntityRendererFactory.Context context, EntityModelLayer entityModelLayer, EntityModelLayer entityModelLayer2, EntityModelLayer entityModelLayer3) {
        super(context, new ZombieZEntityModel<>(context.getPart(entityModelLayer)), new ZombieZEntityModel<>(context.getPart(entityModelLayer2)), new ZombieZEntityModel<>(context.getPart(entityModelLayer3)));
    }

    @Override
    public Identifier getTexture(ZombieZEntity entity) {
        return new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/zombie/zombie_z.png");
    }

}
