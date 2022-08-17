package club.theabyss.server.game.mechanics.flashbang;

import club.theabyss.TheAbyssManager;
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
            this.flashBangData = TheAbyssManager.gson().fromJson(jsonConfig.getJsonObject(), FlashBangData.class);
        }
    }

    @Override
    public void save(JsonConfig jsonConfig) {
        jsonConfig.setJsonObject(TheAbyssManager.gson().toJsonTree(flashBangData).getAsJsonObject());
        try {
            jsonConfig.save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void flash(ServerPlayerEntity player, int flashSeconds) {
        flashBangDataMap().putIfAbsent(player.getUuid(), new OpacityData());
        var data = flashBangDataMap().get(player.getUuid());

        data.setOpacity(255f);
        data.setFlashSeconds(flashSeconds);

        ServerPlayNetworking.send(player, FlashBangS2CFlashPacket.ID, new FlashBangS2CFlashPacket(data.getOpacity(), flashSeconds).write());
    }

    public void updateData(ServerPlayerEntity player, float opacity, int flashSeconds) {
        ServerPlayNetworking.send(player, FlashBangS2CFlashPacket.ID, new FlashBangS2CFlashPacket(opacity, flashSeconds).write());
    }

    public Map<UUID, OpacityData> flashBangDataMap() {
        return flashBangData.getFlashBangDataMap();
    }

    public static class OpacityData {

        private @Getter @Setter float opacity;
        private @Getter @Setter int flashSeconds;

        public OpacityData(float opacity, int flashSeconds) {
            this.opacity = opacity;
            this.flashSeconds = flashSeconds;
        }

        public OpacityData() {
            this(0, 0);
        }

    }

}