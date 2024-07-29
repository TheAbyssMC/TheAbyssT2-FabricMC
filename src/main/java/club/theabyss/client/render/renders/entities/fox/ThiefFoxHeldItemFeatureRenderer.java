package club.theabyss.client.render.renders.entities.fox;

import club.theabyss.client.render.models.entities.fox.ThiefFoxModel;
import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
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

public class ThiefFoxHeldItemFeatureRenderer extends FeatureRenderer<ThiefFoxEntity, ThiefFoxModel<ThiefFoxEntity>> {

    public ThiefFoxHeldItemFeatureRenderer(FeatureRendererContext<ThiefFoxEntity, ThiefFoxModel<ThiefFoxEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, ThiefFoxEntity foxEntity, float f, float g, float h, float j, float k, float l) {
        float m;
        boolean bl = foxEntity.isSleeping();
        boolean bl2 = foxEntity.isBaby();
        matrixStack.push();
        if (bl2) {
            m = 0.75f;
            matrixStack.scale(0.75f, 0.75f, 0.75f);
            matrixStack.translate(0.0, 0.5, 0.209375f);
        }
        matrixStack.translate((this.getContextModel()).head.pivotX / 16.0f, (this.getContextModel()).head.pivotY / 16.0f, (this.getContextModel()).head.pivotZ / 16.0f);
        m = foxEntity.getHeadRoll(h);
        matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(m));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(k));
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(l));
        if (foxEntity.isBaby()) {
            if (bl) {
                matrixStack.translate(0.4f, 0.26f, 0.15f);
            } else {
                matrixStack.translate(0.06f, 0.26f, -0.5);
            }
        } else if (bl) {
            matrixStack.translate(0.46f, 0.26f, 0.22f);
        } else {
            matrixStack.translate(0.06f, 0.27f, -0.5);
        }
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(90.0f));
        if (bl) {
            matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
        ItemStack itemStack = foxEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(foxEntity, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }

}
