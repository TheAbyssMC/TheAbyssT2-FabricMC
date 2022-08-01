package club.theabyss.global.mixins.server.entites;

import club.theabyss.global.utils.GlobalGameManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void modifyLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!(GlobalGameManager.getNowDay() >= 7)) return;

        var entity = ((LivingEntity)(Object)this);
        var entityType = ((LivingEntity)(Object)this).getType();

        if (PassiveEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass()) || FishEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass())) {
            if (entityType.equals(EntityType.TURTLE) || entityType.equals(EntityType.SQUID)) return;
            ci.cancel();
        }
    }

}
