package club.theabyss.global.mixins.server.world;

import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Shadow @Final List<ServerPlayerEntity> players;

    @Shadow @Final private ServerWorldProperties worldProperties;

    // Sends the WorldTimeUpdateS2CPacket to all the online players immediately after changing the world time to fix some timing bugs.
    @Inject(at = @At("TAIL"), method = "setTimeOfDay")
    public void fixTimePacketBug(long timeOfDay, CallbackInfo ci) {
        players.forEach(p -> p.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(worldProperties.getTime(), worldProperties.getTimeOfDay(), worldProperties.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE))));
    }

}
