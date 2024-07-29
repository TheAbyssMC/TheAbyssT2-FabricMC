package club.theabyss.client.render.renders.entities.fox;

import club.theabyss.client.render.models.entities.fox.ThiefFoxModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

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

public class ThiefFoxEntityRenderer extends MobEntityRenderer<ThiefFoxEntity, ThiefFoxModel<ThiefFoxEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/fox/thief_fox.png");
    private static final Identifier SLEEPING_TEXTURE = new Identifier(TheAbyssConstants.MOD_ID,"entities/textures/fox/thief_fox_sleep.png");

    public ThiefFoxEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ThiefFoxModel<>(context.getPart(EntityModelLayers.FOX)), 0.4f);
        this.addFeature(new ThiefFoxHeldItemFeatureRenderer(this));
    }

    @Override
    protected void setupTransforms(ThiefFoxEntity foxEntity, MatrixStack matrixStack, float f, float g, float h) {
        super.setupTransforms(foxEntity, matrixStack, f, g, h);
        if (foxEntity.isChasing() || foxEntity.isWalking()) {
            float i = -MathHelper.lerp(h, foxEntity.prevPitch, foxEntity.getPitch());
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(i));
        }
    }

    @Override
    public Identifier getTexture(ThiefFoxEntity foxEntity) {
        return foxEntity.isSleeping() ? SLEEPING_TEXTURE : TEXTURE;
    }

}
