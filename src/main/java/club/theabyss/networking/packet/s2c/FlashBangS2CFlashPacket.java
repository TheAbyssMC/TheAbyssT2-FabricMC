package club.theabyss.networking.packet.s2c;

import club.theabyss.global.utils.TheAbyssConstants;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FlashBangS2CFlashPacket {

    public static Identifier ID = new Identifier(TheAbyssConstants.MOD_ID, "flash_bang_s2c_flash");

    private @Getter final float opacity;
    private @Getter final int flashSeconds;

    private @Getter final int opaqueSeconds;

    private final PacketByteBuf packetByteBuf;

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds, int opaqueTicks) {
        this.opacity = opacity;
        this.flashSeconds = flashSeconds;
        this.opaqueSeconds = opaqueTicks;
        this.packetByteBuf = PacketByteBufs.create();
    }

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds) {
        this(opacity, flashSeconds, 0);
    }

    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        packetByteBuf.writeInt(opaqueSeconds);
        return packetByteBuf;
    }


}
