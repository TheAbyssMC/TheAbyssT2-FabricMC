package club.theabyss.client.render.renders.entities.raids;

import club.theabyss.client.render.models.entities.raids.FireSpectrumEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.raids.FireSpectrumEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
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

public class FireSpectrumEntityRenderer extends BipedEntityRenderer<FireSpectrumEntity, FireSpectrumEntityModel<FireSpectrumEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/fire_spectrum.png");
    private static final Identifier TEXTURE_CHARGING = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/charging/fire_spectrum_charging.png");

    public FireSpectrumEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FireSpectrumEntityModel<>(context.getPart(EntityModelLayers.VEX)), 0.3F);
    }

    @Override
    public Identifier getTexture(FireSpectrumEntity entity) {
        return entity.isCharging() ? TEXTURE_CHARGING : TEXTURE;
    }

    @Override
    protected void scale(FireSpectrumEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4F, 0.4F, 0.4F);
    }

}
