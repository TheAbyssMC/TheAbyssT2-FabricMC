package club.theabyss.server.global.mixins.server.entites.neutral;

import club.theabyss.TheAbyssManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PolarBearEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PolarBearEntity.class)
public class PolarBearEntityMixin extends MobEntity {

    protected PolarBearEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "initGoals")
    public void addAttackGoal(CallbackInfo ci) {
        var day = (TheAbyssManager.getInstance().serverCore().serverGameManager() != null) ? TheAbyssManager.getInstance().serverCore().serverGameManager().day() : 0;
        if (day >= 7) {
            targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
            goalSelector.add(0, new MeleeAttackGoal(((PolarBearEntity) (Object) this), 1.0D, false));
        }
    }

}
