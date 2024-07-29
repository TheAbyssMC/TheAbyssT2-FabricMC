package club.theabyss.global.mixins.client.renderer;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (client.options.hudHidden) {
            FlashBangClientManager.render(new MatrixStack(), (int) FlashBangClientManager.getOpacity(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
            FlashBangClientManager.renderStaticFrame(FlashBangClientManager.getFramebuffer(), FlashBangClientManager.getOpacity(), client.getWindow().getWidth(), client.getWindow().getHeight());
        }
    }

}
