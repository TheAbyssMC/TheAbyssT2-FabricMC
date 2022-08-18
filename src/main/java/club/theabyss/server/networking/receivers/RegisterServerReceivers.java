package club.theabyss.server.networking.receivers;

import club.theabyss.global.utils.GlobalGameManager;
import club.theabyss.networking.packet.c2s.FlashBangC2SDisableFlashPacket;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

public class RegisterServerReceivers {

    public static void init() {
        ServerPlayNetworking.registerGlobalReceiver(FlashBangC2SDisableFlashPacket.ID, (server, player, handler, buf, responseSender) -> {
            var flashBangManager = GlobalGameManager.getFlashBangManager();
            if (flashBangManager == null) throw new IllegalStateException("The FlashBangManager is null.");

            var opacityData = flashBangManager.getFlashBangData().getFlashBangDataMap().get(player.getUuid());
            if (opacityData == null) return;

            opacityData.setOpacity(buf.readFloat());
            opacityData.setFlashSeconds(buf.readInt());
            opacityData.setOpaqueTicks(buf.readInt());
        });
    }

}
