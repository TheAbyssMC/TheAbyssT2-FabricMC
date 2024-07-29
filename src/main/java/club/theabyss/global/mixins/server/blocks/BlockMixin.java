package club.theabyss.global.mixins.server.blocks;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void manageExperienceDrops(ServerWorld world, BlockPos pos, int size, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() >= 7) ci.cancel();
    }

}
