package club.theabyss.global.mixins.server.blocks;

import club.theabyss.global.utils.GlobalGameManager;
import net.minecraft.block.CactusBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(CactusBlock.class)
public class CactusBlockMixin {

    @ModifyArg(method = "onEntityCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    private float modifyCollisionDamage(float amount) {
        var day = GlobalGameManager.getNowDay();

        if (day >= 7 && day < 14) {
            return amount * 30f;
        } else if (day >= 14) {
            return Short.MAX_VALUE;
        }
        return amount;
    }

}
