package club.theabyss.server.game.entity.entities;

import club.theabyss.server.game.entity.entities.fox.ThiefFoxEntity;
import club.theabyss.server.game.entity.entities.raids.ElementalSpectrumEntity;
import club.theabyss.server.game.entity.entities.spiders.BlackWidowSpiderEntity;
import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import club.theabyss.server.game.entity.entities.zombie.ZombieZEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

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
