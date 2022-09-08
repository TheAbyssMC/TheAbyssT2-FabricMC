package club.theabyss.global.mixins.client.entity;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {

    @Shadow @Final protected MinecraftClient client;

    @Inject(method = "tick", at = @At("TAIL"))
    private void injectOnTick(CallbackInfo ci) {
        FlashBangClientManager.tick();
        FlashBangClientManager.playFlashSound(((ClientPlayerEntity) (Object) this), client.world);
    }

}
