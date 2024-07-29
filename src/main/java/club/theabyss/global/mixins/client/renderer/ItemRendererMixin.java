package club.theabyss.global.mixins.client.renderer;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

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

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;"),
            index = 8, argsOnly = true)
    public BakedModel useTwilightTridentModel(BakedModel value, ItemStack stack) {
        if (stack.isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            return ((ItemRendererAccessor) this).theabyss2$getModels().getModelManager().getModel(new ModelIdentifier(TheAbyssConstants.MOD_ID, "netherite_trident", "inventory"));
        }
        return value;
    }

    @ModifyVariable(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;", shift = At.Shift.BY, by = 2), index = 5)
    public BakedModel getHeldTwilightTridentModel(BakedModel value, ItemStack stack) {
        if (stack.isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            return ((ItemRendererAccessor) this).theabyss2$getModels().getModelManager().getModel(new ModelIdentifier(TheAbyssConstants.MOD_ID, "twilight_trident_in_hand", "inventory"));
        }
        return value;
    }

}
