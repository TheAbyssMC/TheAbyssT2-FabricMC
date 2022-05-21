package club.theabyss.server.global.mixins.entites.neutral;

import club.theabyss.TheAbyssManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin extends PathAwareEntity {

    protected IronGolemEntityMixin(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initGoals", cancellable = true)
    public void addAttackGoal(CallbackInfo ci) {
        var day = (TheAbyssManager.getInstance().serverCore().serverGameManager() != null) ? TheAbyssManager.getInstance().serverCore().serverGameManager().day() : 0;
        if (day >= 7) targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.add(2, new WanderNearTargetGoal(this, 0.9D, 32.0F));
        this.goalSelector.add(2, new WanderAroundPointOfInterestGoal(this, 0.6D, false));
        this.goalSelector.add(4, new IronGolemWanderAroundGoal(this, 0.6D));
        this.goalSelector.add(5, new IronGolemLookGoal(((IronGolemEntity)(Object)this)));
        this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new TrackIronGolemTargetGoal(((IronGolemEntity)(Object)this)));
        this.targetSelector.add(2, new RevengeGoal(this));
        this.targetSelector.add(3, new ActiveTargetGoal<>(this, MobEntity.class, 5, false, false, (entity) -> entity instanceof Monster && !(entity instanceof CreeperEntity)));
        this.targetSelector.add(4, new UniversalAngerGoal<>(((IronGolemEntity)(Object)this), false));

        ci.cancel();
    }

}
