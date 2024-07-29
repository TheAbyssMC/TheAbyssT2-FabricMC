package club.theabyss.server.game.entity.entities.raids;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.world.World;

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

public class ElementalSpectrumEntity extends VexEntity implements Monster {

    public ElementalSpectrumEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
        setHealth(14 * 1.25F);
    }

    public static DefaultAttributeContainer.Builder createElementalSpectrumAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14 * 1.25F).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 13.5 * 1.15F);
    }

}
