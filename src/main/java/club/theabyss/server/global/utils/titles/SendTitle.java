package club.theabyss.server.global.utils.titles;

import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/**
 * Utility class designed to make the process of sending titles to players easier using packets.
 * @author <a href="https://github.com/zLofro">Lofro</a>
 */
public class SendTitle {

    /**
     * Sends a title to the given player.
     * @param player to send the title.
     * @param title to send to the player.
     * @param subtitle to send to the player.
     */
    public static void send(ServerPlayerEntity player, Text title, Text subtitle) {
        send(player, title);

        var subtitleS2CPacket = new SubtitleS2CPacket(subtitle);
        player.networkHandler.sendPacket(subtitleS2CPacket);
    }

    /**
     * Sends a title to the given player.
     * @param player to send the title.
     * @param title to send to the player.
     */
    public static void send(ServerPlayerEntity player, Text title) {
        var titleS2CPacket = new TitleS2CPacket(title);

        player.networkHandler.sendPacket(titleS2CPacket);
    }

    /**
     * Sends a title to the given player.
     * @param player to send the title.
     * @param title to send to the player.
     * @param subtitle to send to the player.
     * @param fadeIn time the text takes to fade in.
     * @param stay time the text stays in the screen.
     * @param fadeOut time the text takes to fade out.
     */
    public static void send(ServerPlayerEntity player, Text title, Text subtitle, int fadeIn, int stay, int fadeOut) {
        send(player, title, subtitle);

        var titleFadeS2CPacket = new TitleFadeS2CPacket(fadeIn, stay, fadeOut);
        player.networkHandler.sendPacket(titleFadeS2CPacket);
    }

}
