package club.theabyss.server.networking.receivers;

import club.theabyss.networking.packet.c2s.FlashBangC2SDisableFlashPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class RegisterServerReceivers {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(FlashBangC2SDisableFlashPacket.ID, FlashBangC2SDisableFlashPacket::registerReceiver);
    }

}
