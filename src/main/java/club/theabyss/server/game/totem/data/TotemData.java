package club.theabyss.server.game.totem.data;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TotemData {

    private final @Getter Map<UUID, Long> usedTotemsMap;

    public TotemData() {
        this.usedTotemsMap = new HashMap<>();
    }

    public TotemData(Map<UUID, Long> usedTotemsMap) {
        this.usedTotemsMap = usedTotemsMap;
    }

}
