package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.bosses.BedrockWither;
import club.theabyss.server.game.entity.entities.creepers.ChargedCreeperEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheAbyssEntities {

    public static final EntityType<BedrockWither> BEDROCK_WITHER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("theabyss2", "bedrock_wither"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BedrockWither::new).dimensions(EntityDimensions.fixed(0.9f, 3.5f))
                    .build()
    );

    public static final EntityType<WeaverSpiderEntity> WEAVER_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("theabyss2", "weaver_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WeaverSpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f, 0.9f))
                    .build()
    );

    public static final EntityType<ChargedCreeperEntity> CHARGED_CREEPER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("theabyss2", "charged_creeper"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ChargedCreeperEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.7f))
                    .build()
    );

}
