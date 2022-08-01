package club.theabyss.global.mixins.server.entites;

import club.theabyss.global.utils.GlobalGameManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "isImmuneToExplosion", at = @At("TAIL"), cancellable = true)
    private void modifyExplosionImmunity(CallbackInfoReturnable<Boolean> cir) {
        var day = GlobalGameManager.getNowDay();
        cir.setReturnValue(day >= 7 ? !(((Entity)(Object)this).getType().equals(EntityType.PLAYER)) : cir.getReturnValue());
    }

}
