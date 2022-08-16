package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class AbyssEntityRegistries {

    public static void register() {
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntity.createWeaverSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.THIEF_FOX, ThiefFoxEntity.createThiefFoxAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.KRAKEN, KrakenEntity.createSquidAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.BLACK_WIDOW_SPIDER, BlackWidowSpiderEntity.createWeaverSpiderAttributes());
    }

}
