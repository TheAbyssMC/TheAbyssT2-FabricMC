package club.theabyss.global.mixins.server.entites.hostile;

import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.global.utils.NullableOptional;
import club.theabyss.server.global.utils.DataTrackerChanger;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

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

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin {

    @Shadow @Final private static TrackedData<Boolean> CHARGED;

    private static final Supplier<NullableOptional<Boolean>> func = () -> {
        var day = GlobalDataAccess.getNowDay();
        return ((day >= 7) ? NullableOptional.of(true) : NullableOptional.absent());
    };

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(EntityType<?> entityType, World world, CallbackInfo ci) {
        DataTrackerChanger.computeFlag(((CreeperEntity)(Object)this), CHARGED, func, false);
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "initDataTracker", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;startTracking(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V", ordinal = 1), index = 1)
    private <T> T modifyChargedDataTrackerOnInitDataTracker(T initialValue) {
        return (T) func.get().getOrDefault((Boolean) initialValue);
    }

    @SuppressWarnings("unchecked")
    @ModifyArg(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/data/DataTracker;set(Lnet/minecraft/entity/data/TrackedData;Ljava/lang/Object;)V"), index = 1)
    private <T> T modifyChargedDataTrackerOnReadNBT(T value) {
        return (T) func.get().getOrDefault((Boolean) value);
    }

}
