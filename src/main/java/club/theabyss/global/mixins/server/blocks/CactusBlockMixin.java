package club.theabyss.global.mixins.server.blocks;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.block.CactusBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

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

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @ModifyArg(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float modifyCollisionDamage(float amount) {
        var day = GlobalDataAccess.getNowDay();

        if (day >= 7 && day < 14) {
            return amount * 30f;
        } else if (day >= 14) {
            return Short.MAX_VALUE;
        }
        return amount;
    }

}
