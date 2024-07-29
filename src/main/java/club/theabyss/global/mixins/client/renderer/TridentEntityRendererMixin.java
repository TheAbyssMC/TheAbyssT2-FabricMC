package club.theabyss.global.mixins.client.renderer;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At(value = "HEAD"), cancellable = true)
    public void getTextureMixin(TridentEntity entity, CallbackInfoReturnable<Identifier> cir) {
        if(((ITridentEntity)entity).getTridentStack$0().isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            cir.setReturnValue(new Identifier(TheAbyssConstants.MOD_ID,"textures/entity/twilight_trident.png"));
        }
    }

}
