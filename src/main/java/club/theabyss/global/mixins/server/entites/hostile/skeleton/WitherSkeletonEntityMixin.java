package club.theabyss.global.mixins.server.entites.hostile.skeleton;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.LocalDifficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherSkeletonEntity.class)
public class WitherSkeletonEntityMixin {

    @Inject(method = "initEquipment", at = @At("HEAD"), cancellable = true)
    private void modifyEquipment(LocalDifficulty difficulty, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() < 7) return;

        ((AbstractSkeletonEntity)(Object)this).equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.DIAMOND_SWORD));
        ci.cancel();
    }

}
