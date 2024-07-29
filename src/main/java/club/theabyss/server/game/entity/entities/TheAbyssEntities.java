package club.theabyss.server.game.entity.entities;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import club.theabyss.server.game.entity.entities.raids.*;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import club.theabyss.server.game.entity.entities.zombie.ZombieZEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class TheAbyssEntities {

    /*public static final EntityType<BedrockWither> BEDROCK_WITHER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier("theabyss2", "bedrock_wither"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BedrockWither::new).dimensions(EntityDimensions.fixed(0.9f, 3.5f))
                    .build()
    );*/

    public static final EntityType<BlackWidowSpiderEntity> BLACK_WIDOW_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "black_widow"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BlackWidowSpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f, 1.6f))
                    .build()
    );

    public static final EntityType<WeaverSpiderEntity> WEAVER_SPIDER = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "weaver_spider"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WeaverSpiderEntity::new)
                    .dimensions(EntityDimensions.fixed(1.4f, 1.6f))
                    .build()
    );

    public static final EntityType<ThiefFoxEntity> THIEF_FOX = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "thief_fox"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ThiefFoxEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 0.7f))
                    .build()
    );

    public static final EntityType<KrakenEntity> KRAKEN = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "kraken"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, KrakenEntity::new)
                    .dimensions(EntityDimensions.fixed(0.8f, 1.4f))
                    .build()
    );

    public static final EntityType<BloodSpectrumEntity> BLOOD_SPECTRUM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "blood_spectrum"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, BloodSpectrumEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f))
                    .build()
    );

    public static final EntityType<FireSpectrumEntity> FIRE_SPECTRUM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "fire_spectrum"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FireSpectrumEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f))
                    .build()
    );

    public static final EntityType<WindSpectrumEntity> WIND_SPECTRUM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "wind_spectrum"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, WindSpectrumEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f))
                    .build()
    );

    public static final EntityType<FrostSpectrumEntity> FROST_SPECTRUM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "frost_spectrum"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, FrostSpectrumEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f))
                    .build()
    );

    public static final EntityType<ShadowSpectrumEntity> SHADOW_SPECTRUM = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "shadow_spectrum"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ShadowSpectrumEntity::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.8f))
                    .build()
    );

    public static final EntityType<ZombieZEntity> ZOMBIE_Z = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(TheAbyssConstants.MOD_ID, "zombie_z"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ZombieZEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f, 1.95f))
                    .build()
    );


}
