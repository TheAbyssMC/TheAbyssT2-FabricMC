package club.theabyss.global.mixins.client.sound;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SoundSystem.class)
public class SoundSystemMixin {

    @Redirect(method = "tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/sound/SoundSystem;getAdjustedVolume(Lnet/minecraft/client/sound/SoundInstance;)F"))
    private float redirectRun(SoundSystem instance, SoundInstance sound) {

        return 0;
    }

}
