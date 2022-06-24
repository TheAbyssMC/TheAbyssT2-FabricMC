package club.theabyss.server.global.mixins.server.network;

import club.theabyss.server.global.events.ServerPlayerConnectionEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public abstract class ServerPlayNetworkHandlerMixin {
    @Shadow public ServerPlayerEntity player;

    @Shadow @Final private MinecraftServer server;

    @Inject(at = @At("HEAD"), method = "onDisconnected")
    public void onDisconnect(Text reason, CallbackInfo ci) {
        ServerPlayerConnectionEvents.OnServerPlayerDisconnect.EVENT.invoker().disconnect(player, server);
    }

}
