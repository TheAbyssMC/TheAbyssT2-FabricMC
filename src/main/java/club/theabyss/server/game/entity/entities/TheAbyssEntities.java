package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.bosses.BedrockWither;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheAbyssEntities {

    public static final EntityType<BedrockWither> BEDROCK_WITHER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("theabyss2", "bedrockwither"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BedrockWither::new).dimensions(EntityDimensions.fixed(0.9f, 3.5f))
                    .build());

}