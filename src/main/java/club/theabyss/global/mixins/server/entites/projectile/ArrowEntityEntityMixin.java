package club.theabyss.global.mixins.server.entites.projectile;

import club.theabyss.global.interfaces.server.entity.projectile.IArrowEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
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

@Mixin(ArrowEntity.class)
public abstract class ArrowEntityEntityMixin extends PersistentProjectileEntity implements IArrowEntity {

    @Unique private static final TrackedData<Byte> SHOT_FROM_TWILIGHT;

    protected ArrowEntityEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onHit", at = @At("HEAD"))
    private void modifyOnHit(LivingEntity livingEntity, CallbackInfo ci) {
        if (isShotFromTwilight()) {
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 100, 0, true, true));
            livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 1, true, true));
        }
    }

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    private void modifyInitDataTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(SHOT_FROM_TWILIGHT, (byte)0);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void modifyReadCustomDataFromNBT(NbtCompound nbtCompound, CallbackInfo ci) {
        setShotFromTwilight(nbtCompound.getBoolean("ShotFromTwilight"));
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void modifyWriteCustomDataToNBT(NbtCompound nbtCompound, CallbackInfo ci) {
        nbtCompound.putBoolean("ShotFromTwilight", isShotFromTwilight());
    }

    @Unique
    public boolean isShotFromTwilight() {
        byte b = this.dataTracker.get(SHOT_FROM_TWILIGHT);
        return b == (byte) 1;
    }

    @Unique
    public void setShotFromTwilight(boolean shotFromTwilight) {
        this.dataTracker.set(SHOT_FROM_TWILIGHT, shotFromTwilight ? (byte) 1 : (byte) 0);
    }

    @Override
    public void setShotFromTwilight$0(boolean shotFromTwilight) {
        setShotFromTwilight(shotFromTwilight);
    }

    static {
        SHOT_FROM_TWILIGHT = DataTracker.registerData(ArrowEntity.class, TrackedDataHandlerRegistry.BYTE);
    }

}
