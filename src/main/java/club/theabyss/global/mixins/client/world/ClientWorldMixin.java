package club.theabyss.global.mixins.client.world;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import club.theabyss.networking.packet.c2s.FlashBangC2SDisableFlashPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void injectIntoDisconnect(CallbackInfo ci) {
        ClientPlayNetworking.send(FlashBangC2SDisableFlashPacket.ID, new FlashBangC2SDisableFlashPacket(FlashBangClientManager.getOpacity(), FlashBangClientManager.getSoundVolume(), FlashBangClientManager.getFlashSeconds(), FlashBangClientManager.getOpaqueTicks()).write());
        FlashBangClientManager.reset();
    }

}
