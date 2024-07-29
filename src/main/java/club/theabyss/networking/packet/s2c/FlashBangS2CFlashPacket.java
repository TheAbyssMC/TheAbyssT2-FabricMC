package club.theabyss.networking.packet.s2c;

import club.theabyss.global.utils.TheAbyssConstants;
import lombok.Getter;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

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

public class FlashBangS2CFlashPacket {

    public static Identifier ID = new Identifier(TheAbyssConstants.MOD_ID, "flash_bang_s2c_flash");

    private @Getter final float opacity;
    private @Getter final float soundVolume;
    private @Getter final int flashSeconds;
    private @Getter final int opaqueSeconds;
    private @Getter final boolean showStaticFrame;
    private final PacketByteBuf packetByteBuf;

    public FlashBangS2CFlashPacket(float opacity, float soundVolume, int flashSeconds, int opaqueTicks, boolean showStaticFrame) {
        this.opacity = opacity;
        this.soundVolume = soundVolume;
        this.flashSeconds = flashSeconds;
        this.opaqueSeconds = opaqueTicks;
        this.showStaticFrame = showStaticFrame;
        this.packetByteBuf = PacketByteBufs.create();
    }

    public FlashBangS2CFlashPacket(float opacity, int flashSeconds) {
        this(opacity, 0, flashSeconds, 0, true);
    }

    public PacketByteBuf write() {
        packetByteBuf.writeFloat(opacity);
        packetByteBuf.writeInt(flashSeconds);
        packetByteBuf.writeInt(opaqueSeconds);
        packetByteBuf.writeBoolean(showStaticFrame);
        return packetByteBuf;
    }

}
