package club.theabyss.global.interfaces.server.entity.goat;

import net.minecraft.entity.passive.GoatEntity;

public interface IGoatEntity {

    default GoatEntity getGoatEntity() {
        return null;
    }

}
