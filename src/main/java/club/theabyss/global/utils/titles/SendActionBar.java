package club.theabyss.global.utils.titles;

import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SendActionBar {

    public static void send(ServerPlayerEntity player, Text title) {
        var packet = new OverlayMessageS2CPacket(title);

        player.networkHandler.sendPacket(packet);
    }

}
