package club.theabyss.global.utils;

import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class SendTitle {

    public static void send(ServerPlayerEntity player, Text title, Text subtitle) {
        send(player, title);

        var subtitleS2CPacket = new SubtitleS2CPacket(subtitle);
        player.networkHandler.sendPacket(subtitleS2CPacket);
    }

    public static void send(ServerPlayerEntity player, Text title) {
        var titleS2CPacket = new TitleS2CPacket(title);

        player.networkHandler.sendPacket(titleS2CPacket);
    }

    public static void send(ServerPlayerEntity player, Text title, Text subtitle, int fadeIn, int stay, int fadeOut) {
        send(player, title, subtitle);

        var titleFadeS2CPacket = new TitleFadeS2CPacket(fadeIn, stay, fadeOut);
        player.networkHandler.sendPacket(titleFadeS2CPacket);
    }

}
