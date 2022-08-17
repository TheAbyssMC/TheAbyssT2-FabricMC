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

    private final PacketByteBuf packetByteBuf;

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds) {
        this.opacity = opacity;
        this.flashSeconds = flashSeconds;
        this.packetByteBuf = PacketByteBufs.create();
    }

    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        return packetByteBuf;
    }


}
