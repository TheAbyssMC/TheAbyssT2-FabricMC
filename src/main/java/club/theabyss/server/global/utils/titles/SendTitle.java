package club.theabyss.server.global.utils.titles;

import net.minecraft.network.packet.s2c.play.SubtitleS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleFadeS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
