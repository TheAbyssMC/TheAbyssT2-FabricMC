package club.theabyss.networking.packet.s2c;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import club.theabyss.global.utils.TheAbyssConstants;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class FlashBangS2CFlashPacket {

    public static Identifier ID = new Identifier(TheAbyssConstants.MOD_ID, "flash_bang_s2c_flash");

    private @Getter final float opacity;
    private @Getter final int flashSeconds;

    private @Getter final int opaqueSeconds;

    private @Getter final boolean showStaticFrame;

    private final PacketByteBuf packetByteBuf;

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds, int opaqueTicks, boolean showStaticFrame) {
        this.opacity = opacity;
        this.flashSeconds = flashSeconds;
        this.opaqueSeconds = opaqueTicks;
        this.showStaticFrame = showStaticFrame;
        this.packetByteBuf = PacketByteBufs.create();
    }

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds) {
        this(opacity, flashSeconds, 0, true);
    }

    public static void registerReceiver(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender responseSender) {
        FlashBangClientManager.reset();
        FlashBangClientManager.setOpacity(buf.readFloat());
        FlashBangClientManager.setFlashSeconds(buf.readInt());
        FlashBangClientManager.setOpaqueTicks(buf.readInt());
        var showStaticImage = buf.readBoolean();
        MinecraftClient.getInstance().execute(() -> {
            if (showStaticImage && client.getWindow().getWidth() > 0 && client.getWindow().getHeight() > 0)
                FlashBangClientManager.setFramebuffer(FlashBangClientManager.copyColorsFrom(client.getFramebuffer(), new SimpleFramebuffer(client.getWindow().getWidth(), client.getWindow().getHeight(), true, false)));
            FlashBangClientManager.setShouldTick(true);
        });
    }

    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        packetByteBuf.writeInt(opaqueSeconds);
        packetByteBuf.writeBoolean(showStaticFrame);
        return packetByteBuf;
    }


}
