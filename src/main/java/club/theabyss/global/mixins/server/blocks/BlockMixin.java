package club.theabyss.global.mixins.server.blocks;

import club.theabyss.global.utils.GlobalDataAccess;
import net.minecraft.block.Block;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {

    @Inject(method = "dropExperience", at = @At("HEAD"), cancellable = true)
    private void manageExperienceDrops(ServerWorld world, BlockPos pos, int size, CallbackInfo ci) {
        if (GlobalDataAccess.getNowDay() >= 7) ci.cancel();
    }

}
