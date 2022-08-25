package club.theabyss.client.networking.receivers;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import club.theabyss.networking.packet.s2c.FlashBangS2CFlashPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.SimpleFramebuffer;

public class RegisterClientReceivers {

    public static void init() {
        //TODO DO THIS WITH A STATIC FUNCTION FROM THE PACKET INSTEAD OF A LAMBDA.
        ClientPlayNetworking.registerGlobalReceiver(FlashBangS2CFlashPacket.ID, (client, handler, buf, responseSender) -> {
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
        });
    }

}
