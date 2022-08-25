package club.theabyss.networking.packet.c2s;

import club.theabyss.global.utils.GlobalGameManager;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FlashBangC2SDisableFlashPacket {

    public static final Identifier ID = new Identifier("flash_bang_c2s_disable_flash");

    private @Getter final float opacity;
    private @Getter final int flashSeconds;
    private @Getter final int opaqueSeconds;

    private final PacketByteBuf buf;

    public FlashBangC2SDisableFlashPacket(float opacity, int flashSeconds, int opaqueTicks) {
        this.opacity = opacity;
        this.flashSeconds = flashSeconds;
        this.opaqueSeconds = opaqueTicks;
        this.buf = PacketByteBufs.create();
    }

    public PacketByteBuf write() {
        buf.writeFloat(opacity);
        buf.writeInt(flashSeconds);
        buf.writeInt(opaqueSeconds);
        return buf;
    }

    public static void registerReceiver(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        var flashBangManager = GlobalGameManager.getFlashBangManager();
        if (flashBangManager == null) throw new IllegalStateException("The FlashBangManager is null.");

        var opacityData = flashBangManager.getFlashBangData().getFlashBangDataMap().get(player.getUuid());
        if (opacityData == null) return;

        opacityData.setOpacity(buf.readFloat());
        opacityData.setFlashSeconds(buf.readInt());
        opacityData.setOpaqueTicks(buf.readInt());
    }

}
