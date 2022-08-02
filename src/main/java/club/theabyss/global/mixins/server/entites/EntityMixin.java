package club.theabyss.global.mixins.server.entites;

import club.theabyss.global.utils.GlobalGameManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isImmuneToExplosion", at = @At("TAIL"), cancellable = true)
    private void modifyExplosionImmunity(CallbackInfoReturnable<Boolean> cir) {
        var day = GlobalGameManager.getNowDay();
        cir.setReturnValue(day >= 7 ? !(((Entity)(Object)this).getType().equals(EntityType.PLAYER)) : cir.getReturnValue());
    }

    @ModifyArg(method = "baseTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", ordinal = 0), index = 1)
    private float modifyFireDamage(float amount) {
        return GlobalGameManager.getNowDay() >= 7 ? amount * 2.5f : amount;
    }

    @ModifyArg(method = "setOnFireFromLava", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float modifyLavaDamage(float amount) {
        return GlobalGameManager.getNowDay() >= 7 ? amount * 2.5f : amount;
    }

}
