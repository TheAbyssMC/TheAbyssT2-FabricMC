package club.theabyss.global.mixins.server.entites.hostile;

import club.theabyss.global.utils.GlobalGameManager;
import net.minecraft.entity.mob.MagmaCubeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MagmaCubeEntity.class)
public class MagmaCubeEntityMixin {

    @Inject(method = "getDamageAmount", at = @At("TAIL"), cancellable = true)
    private void modifyAttackDamage(CallbackInfoReturnable<Float> cir) {
        var day = GlobalGameManager.getNowDay();
        cir.setReturnValue(day >= 7 ? cir.getReturnValue() * 3 : cir.getReturnValue());
    }

}
