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
        return (GlobalGameManager.getNowDay() >= 7) ? amount * 30f : amount;
    }

}
