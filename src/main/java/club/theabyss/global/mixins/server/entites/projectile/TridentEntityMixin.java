package club.theabyss.global.mixins.server.entites.projectile;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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

@Mixin(TridentEntity.class)
public class TridentEntityMixin implements ITridentEntity {

    @Shadow private ItemStack tridentStack;

    @Override
    public ItemStack getTridentStack$0() {
        return tridentStack;
    }

    @Override
    public void setTridentStack$0(ItemStack itemStack) {
        tridentStack = itemStack;
    }

}
