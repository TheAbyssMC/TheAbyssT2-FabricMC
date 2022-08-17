package club.theabyss.networking.packet.c2s;

import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FlashBangC2SDisableFlashPacket {

    private static @Getter final Identifier ID = new Identifier("flash_bang_c2s_disable_flash");

    private @Getter final float opacity;
    private @Getter final int flashSeconds;

    private final PacketByteBuf buf;

    public FlashBangC2SDisableFlashPacket(float opacity, int flashSeconds) {
        this.opacity = opacity;
        this.flashSeconds = flashSeconds;
        this.buf = PacketByteBufs.create();
    }

    public PacketByteBuf write() {
        buf.writeFloat(opacity);
        buf.writeInt(flashSeconds);
        return buf;
    }

}
