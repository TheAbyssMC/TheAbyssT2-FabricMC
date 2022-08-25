package club.theabyss.client.networking.receivers;

import club.theabyss.networking.packet.s2c.FlashBangS2CFlashPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class RegisterClientReceivers {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(FlashBangS2CFlashPacket.ID, FlashBangS2CFlashPacket::registerReceiver);
    }

}
