package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.entity.mob.CreeperEntity;

public class AbyssEntityRegistries {

    public static void register() {
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntity.createWeaverSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.CHARGED_CREEPER, CreeperEntity.createCreeperAttributes());
    }

}
