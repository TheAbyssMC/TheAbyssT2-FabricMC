package club.theabyss.server.game.mechanics.flashbang.data;

import club.theabyss.server.game.mechanics.flashbang.FlashBangServerManager;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlashBangData {

    private @Getter final Map<UUID, FlashBangServerManager.OpacityData> flashBangDataMap;

    public FlashBangData(Map<UUID, FlashBangServerManager.OpacityData> flashBangDataMap) {
        this.flashBangDataMap = flashBangDataMap;
    }

    public FlashBangData() {
        this(new HashMap<>());
    }

}
