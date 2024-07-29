package club.theabyss.global.mixins.server.entites.hostile;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.entity.mob.MagmaCubeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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

@Mixin(MagmaCubeEntity.class)
public class MagmaCubeEntityMixin {

    @Inject(method = "getDamageAmount", at = @At("TAIL"), cancellable = true)
    private void modifyAttackDamage(CallbackInfoReturnable<Float> cir) {
        var day = GlobalDataAccess.getNowDay();
        cir.setReturnValue(day >= 7 ? cir.getReturnValue() * 3 : cir.getReturnValue());
    }

}
