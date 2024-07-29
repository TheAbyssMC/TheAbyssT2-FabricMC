package club.theabyss.client.render;

import club.theabyss.client.render.layers.TheAbyssModelLayers;
import club.theabyss.client.render.models.entities.fox.ThiefFoxModel;
import club.theabyss.client.render.models.entities.raids.*;
import club.theabyss.client.render.models.entities.watercreatures.KrakenEntityModel;
import club.theabyss.client.render.models.entities.zombie.ZombieZEntityModel;
import club.theabyss.client.render.renders.entities.fox.ThiefFoxEntityRenderer;
import club.theabyss.client.render.renders.entities.raids.*;
import club.theabyss.client.render.renders.entities.spiders.BlackWidowSpiderEntityRenderer;
import club.theabyss.client.render.renders.entities.spiders.WeaverSpiderEntityRenderer;
import club.theabyss.client.render.renders.entities.watercreatures.KrakenEntityRenderer;
import club.theabyss.client.render.renders.entities.zombie.ZombieZEntityRenderer;
import club.theabyss.server.game.entity.entities.TheAbyssEntities;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

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

public class RenderRegistries {

    public static void register() {
        EntityRendererRegistry.register(TheAbyssEntities.THIEF_FOX, ThiefFoxEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.THIEF_FOX_MODEL_LAYER, ThiefFoxModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.KRAKEN, KrakenEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.KRAKEN_MODEL_LAYER, KrakenEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntityRenderer::new);
        EntityRendererRegistry.register(TheAbyssEntities.BLACK_WIDOW_SPIDER, BlackWidowSpiderEntityRenderer::new);

        EntityRendererRegistry.register(TheAbyssEntities.BLOOD_SPECTRUM, BloodSpectrumEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.BLOOD_SPECTRUM_LAYER, BloodSpectrumEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.FIRE_SPECTRUM, FireSpectrumEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.FIRE_SPECTRUM_LAYER, FireSpectrumEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.SHADOW_SPECTRUM, ShadowSpectrumEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.SHADOW_SPECTRUM_LAYER, ShadowSpectrumEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.WIND_SPECTRUM, WindSpectrumEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.WIND_SPECTRUM_LAYER, WindSpectrumEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.FROST_SPECTRUM, FrostSpectrumEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.FROST_SPECTRUM_LAYER, FrostSpectrumEntityModel::getTexturedModelData);

        EntityRendererRegistry.register(TheAbyssEntities.ZOMBIE_Z, ZombieZEntityRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(TheAbyssModelLayers.ZOMBIE_Z_LAYER, ZombieZEntityModel::getTexturedModelData);
    }

}
