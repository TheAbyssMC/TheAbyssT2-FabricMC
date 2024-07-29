package club.theabyss.global.mixins.client.sound;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.sound.Channel;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

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

@Mixin(SoundSystem.class)
public abstract class SoundSystemMixin {

    @Shadow protected abstract float getAdjustedVolume(SoundInstance sound);

    @Shadow private boolean started;

    @Shadow @Final private Map<SoundInstance, Channel.SourceManager> sources;

    @Inject(method = "tick()V", at = @At("HEAD"))
    private void redirectRun(CallbackInfo ci) {
        updateCustomVolume();
    }

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundSystem;getAdjustedVolume(Lnet/minecraft/client/sound/SoundInstance;)F"))
    private float redirectTick(SoundSystem instance, SoundInstance sound) {
        return (FlashBangClientManager.isTicking()) ? FlashBangClientManager.getVolumes(sound) * getAdjustedVolume(sound) : getAdjustedVolume(sound);
    }

    @Redirect(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/sound/SoundInstance;getVolume()F"))
    private float redirectVolumeOnPlay(SoundInstance instance) {
        return (FlashBangClientManager.isTicking()) ? FlashBangClientManager.getVolumes(instance) * getAdjustedVolume(instance) : getAdjustedVolume(instance);
    }

    @Redirect(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", at = @At(value = "INVOKE",target = "Lnet/minecraft/client/sound/SoundSystem;getAdjustedVolume(Lnet/minecraft/client/sound/SoundInstance;)F"))
    private float redirectAdjustedVolumeOnPlay(SoundSystem instance, SoundInstance sound) {
        return (FlashBangClientManager.isTicking()) ? FlashBangClientManager.getVolumes(sound) * getAdjustedVolume(sound) : getAdjustedVolume(sound);
    }

    public void updateCustomVolume() {
        if (!started || !FlashBangClientManager.isTicking()) return;
        sources.forEach((source2, sourceManager) -> {
            float f = FlashBangClientManager.getVolumes(source2) * getAdjustedVolume(source2);
            sourceManager.run(source -> {
                if (f <= 0.0f) {
                    source.stop();
                } else {
                    source.setVolume(f);
                }
            });
        });
    }

}
