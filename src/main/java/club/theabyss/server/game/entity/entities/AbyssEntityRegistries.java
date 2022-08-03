package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class AbyssEntityRegistries {

    public static void register() {
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntity.createWeaverSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.THIEF_FOX, ThiefFoxEntity.createThiefFoxAttributes());
    }

}
