package club.theabyss.global.mixins.client;

import club.theabyss.client.TheAbyssClient;
import club.theabyss.client.global.events.ClientStateEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import net.minecraft.client.gui.screen.Screen;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
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

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Shadow @Nullable public Screen currentScreen;
    @Unique
    private Screen prevScreen;

    @Inject(method = "setScreen", at = @At("HEAD"))
    public void modifyHead(Screen screen, CallbackInfo ci) {
        prevScreen = currentScreen;
    }

    @Redirect(method = "setScreen", at = @At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;skipGameRender:Z"))
    public void modifySkipGameRenderer(MinecraftClient instance, boolean value) {
        modifyTail();
    }

    @Inject(method = "setScreen", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Mouse;lockCursor()V"))
    public void modifyLockCursor(Screen screen, CallbackInfo ci) {
        modifyTail();
    }

    @Unique
    private void modifyTail() {
        if (currentScreen == prevScreen) return;

        boolean shouldPause = currentScreen != null && currentScreen.shouldPause();

        if (prevScreen != null && prevScreen.shouldPause() == shouldPause) return;

        (shouldPause ? ClientStateEvents.OnClientPause.EVENT : ClientStateEvents.OnClientResume.EVENT).invoker().action(((MinecraftClient) (Object) this));
    }

    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/resource/ReloadableResourceManagerImpl;reload(Ljava/util/concurrent/Executor;Ljava/util/concurrent/Executor;Ljava/util/concurrent/CompletableFuture;Ljava/util/List;)Lnet/minecraft/resource/ResourceReload;", shift = At.Shift.BEFORE))
    public void init(RunArgs runArgs, CallbackInfo ci) {
        TheAbyssClient.registerBuiltinItemRenderers((MinecraftClient) (Object) this);
    }

}

