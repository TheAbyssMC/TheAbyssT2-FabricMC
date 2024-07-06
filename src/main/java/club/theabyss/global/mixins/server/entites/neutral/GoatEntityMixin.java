package club.theabyss.global.mixins.server.entites.neutral;

import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.game.entity.entities.goat.CustomGoatBrain;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.brain.Activity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.GoatEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoatEntity.class)
public abstract class GoatEntityMixin extends MobEntity {

    @Shadow @Final private static TrackedData<Boolean> SCREAMING;

    @Shadow public abstract void setScreaming(boolean bl);

    @Shadow @Final protected static ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    @Shadow protected abstract Brain.Profile<GoatEntity> createBrainProfile();

    protected GoatEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    private void init(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            var maxHealth = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            var attackDamage = this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);

            if (maxHealth != null) maxHealth.setBaseValue(50.0);
            if (attackDamage != null) attackDamage.setBaseValue(26.0);

            setHealth(50);

            setScreaming(true);
        }
    }

    @Inject(method = "deserializeBrain", at = @At("RETURN"), cancellable = true)
    private void modifyDeserializeBrain(Dynamic<?> dynamic, CallbackInfoReturnable<Brain<?>> cir) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            cir.setReturnValue(CustomGoatBrain.create(((GoatEntity)(Object)this), createBrainProfile().deserialize(dynamic)));
        }
    }

    @Inject(method = "createBrainProfile", at = @At("RETURN"), cancellable = true)
    private void modifyBrainProfile(CallbackInfoReturnable<Brain.Profile<GoatEntity>> cir) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            ImmutableList<SensorType<? extends Sensor<? super GoatEntity>>> SENSORS = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.HURT_BY, SensorType.NEAREST_ITEMS, SensorType.NEAREST_ADULT, SensorType.NEAREST_PLAYERS, SensorType.GOAT_TEMPTATIONS);
            ImmutableList<MemoryModuleType<?>> MEMORY_MODULES = ImmutableList.of(MemoryModuleType.LOOK_TARGET, MemoryModuleType.VISIBLE_MOBS, MemoryModuleType.WALK_TARGET, MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryModuleType.PATH, MemoryModuleType.ATE_RECENTLY, MemoryModuleType.BREED_TARGET, MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryModuleType.LONG_JUMP_MID_JUMP, MemoryModuleType.TEMPTING_PLAYER, MemoryModuleType.NEAREST_VISIBLE_ADULT, MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryModuleType.IS_TEMPTED, MemoryModuleType.RAM_COOLDOWN_TICKS, MemoryModuleType.RAM_TARGET, MemoryModuleType.MOBS, MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleType.HURT_BY, MemoryModuleType.HURT_BY_ENTITY, MemoryModuleType.ATTACK_TARGET, MemoryModuleType.ATTACK_COOLING_DOWN, MemoryModuleType.INTERACTION_TARGET, MemoryModuleType.ANGRY_AT, MemoryModuleType.NEAREST_VISIBLE_NEMESIS);

            cir.setReturnValue(Brain.createProfile(MEMORY_MODULES, SENSORS));
        }
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    private void modifyTick(CallbackInfo ci) {
        brain.resetPossibleActivities(ImmutableList.of(Activity.FIGHT, Activity.IDLE));

        this.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
    }

    @Inject(at = @At("HEAD"), method = "onGrowUp", cancellable = true)
    public void modifyOnGrowUp(CallbackInfo ci) {
        var day = GlobalDataAccess.getNowDay();
        if (day >= 7) ci.cancel();
    }

    @Redirect(method = "createChild(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/PassiveEntity;)Lnet/minecraft/entity/passive/GoatEntity;", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatEntity;setScreaming(Z)V"))
    private void modifyScreamingOnCreateChild(GoatEntity instance, boolean bl) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            this.dataTracker.set(SCREAMING, true);
        } else {
            this.dataTracker.set(SCREAMING, bl);
        }
    }

    @Redirect(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatEntity;setScreaming(Z)V"))
    private void modifyScreamingOnInitialize(GoatEntity instance, boolean bl) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            this.dataTracker.set(SCREAMING, true);
        } else {
            this.dataTracker.set(SCREAMING, bl);
        }
    }

    @Redirect(method = "readCustomDataFromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/GoatEntity;setScreaming(Z)V"))
    private void modifyScreamingOnReadNBT(GoatEntity instance, boolean bl) {
        if (GlobalDataAccess.getNowDay() >= 7) {
            this.dataTracker.set(SCREAMING, true);
        } else {
            this.dataTracker.set(SCREAMING, bl);
        }
    }

}
