package club.theabyss.client.networking;

import club.theabyss.TheAbyssManager;
import club.theabyss.client.render.flashBang.FlashBangClientManager;
import club.theabyss.networking.packet.s2c.FlashBangS2CFlashPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class RegisterClientReceivers {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(FlashBangS2CFlashPacket.ID, (client, handler, buf, responseSender) -> {
            TheAbyssManager.getLogger().info("SE LLAMA");
            FlashBangClientManager.setOpacity(buf.readFloat());
            FlashBangClientManager.setFlashSeconds(buf.readInt());
            FlashBangClientManager.setShouldTick(true);
        });
    }

}
