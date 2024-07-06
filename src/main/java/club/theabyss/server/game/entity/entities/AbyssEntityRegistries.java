package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import club.theabyss.server.game.entity.entities.raids.ElementalSpectrumEntity;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import club.theabyss.server.game.entity.entities.zombie.ZombieZEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public class AbyssEntityRegistries {

    public static void register() {
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.WEAVER_SPIDER, WeaverSpiderEntity.createWeaverSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.THIEF_FOX, ThiefFoxEntity.createThiefFoxAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.ZOMBIE_Z, ZombieZEntity.createZombieZAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.KRAKEN, KrakenEntity.createSquidAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.BLACK_WIDOW_SPIDER, BlackWidowSpiderEntity.createWeaverSpiderAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.SHADOW_SPECTRUM, ElementalSpectrumEntity.createElementalSpectrumAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.BLOOD_SPECTRUM, ElementalSpectrumEntity.createElementalSpectrumAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.WIND_SPECTRUM, ElementalSpectrumEntity.createElementalSpectrumAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.FROST_SPECTRUM, ElementalSpectrumEntity.createElementalSpectrumAttributes());
        FabricDefaultAttributeRegistry.register(TheAbyssEntities.FIRE_SPECTRUM, ElementalSpectrumEntity.createElementalSpectrumAttributes());
    }

}
