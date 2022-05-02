package club.theabyss.server.global.mixins;

import club.theabyss.server.global.events.ServerPlayerConnectionEvents;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;

    @Inject(at = @At("TAIL"), method = "onDisconnected")
    public void onDisconnect(Text reason, CallbackInfo ci) {
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.invoker().disconnect(player);
    }
}
