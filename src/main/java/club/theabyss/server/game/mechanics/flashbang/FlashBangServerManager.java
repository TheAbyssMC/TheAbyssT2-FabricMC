package club.theabyss.server.game.mechanics.flashbang;

import club.theabyss.TheAbyss;
import club.theabyss.global.data.util.JsonConfig;
import club.theabyss.global.interfaces.server.data.Restorable;
import club.theabyss.networking.packet.s2c.FlashBangS2CFlashPacket;
import club.theabyss.server.game.ServerGameManager;
import club.theabyss.server.game.mechanics.flashbang.data.FlashBangData;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Map;
import java.util.UUID;

public class FlashBangServerManager implements Restorable {

    private final ServerGameManager gameManager;

    private @Getter FlashBangData flashBangData;

    public FlashBangServerManager(final ServerGameManager gameManager) {
        this.gameManager = gameManager;
        this.restore(gameManager.serverManager().dataManager().flashBangData());
    }

    @Override
    public void restore(JsonConfig jsonConfig) {
        if (jsonConfig.getJsonObject().entrySet().isEmpty()) {
            this.flashBangData = new FlashBangData();
        } else {
            this.flashBangData = TheAbyss.gson().fromJson(jsonConfig.getJsonObject(), FlashBangData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyss.gson().toJsonTree(flashBangData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flash(ServerPlayerEntity player, int flashSeconds, int opaqueSeconds, boolean showStaticFrame) {
        flashBangDataMap().putIfAbsent(player.getUuid(), new OpacityData());
        var data = flashBangDataMap().get(player.getUuid());

        data.setOpacity(255f);
        data.setFlashSeconds(flashSeconds);
        data.setOpaqueTicks(opaqueSeconds);

        ServerPlayNetworking.send(player, FlashBangS2CFlashPacket.ID, new FlashBangS2CFlashPacket(data.getOpacity(), 0, flashSeconds, opaqueSeconds, showStaticFrame).write());
    }

    public void updateData(ServerPlayerEntity player, float opacity, float soundVolume, int flashSeconds, int opaqueSeconds, boolean showStaticImage) {
        ServerPlayNetworking.send(player, FlashBangS2CFlashPacket.ID, new FlashBangS2CFlashPacket(opacity, soundVolume, flashSeconds, opaqueSeconds, showStaticImage).write());
    }

    public Map<UUID, OpacityData> flashBangDataMap() {
        return flashBangData.getFlashBangDataMap();
    }

    public static class OpacityData {

        private @Getter @Setter float opacity;
        private @Getter @Setter float soundVolume;
        private @Getter @Setter int flashSeconds;
        private @Getter @Setter int opaqueTicks;

        public OpacityData(float opacity, float soundVolume, int flashSeconds, int opaqueTicks) {
            this.opacity = opacity;
            this.soundVolume = soundVolume;
            this.flashSeconds = flashSeconds;
            this.opaqueTicks = opaqueTicks;
        }

        public OpacityData(float opacity, int flashSeconds) {
            this(opacity, 0, flashSeconds, 0);
        }

        public OpacityData() {
            this(0, 0);
        }

    }

}