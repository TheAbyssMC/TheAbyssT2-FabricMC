package club.theabyss.global.mixins.client.hud;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Timer;
import java.util.TimerTask;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Inject(method = "render", at = @At("TAIL"))
    private void test(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        //flashBangEffect(matrices, 10);
    }

    private void flashBangEffect(MatrixStack matrices, int seconds) {
        final int[] opacity = {255};
        int opacityChangePerTick = 255 / seconds * 20;

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (opacity[0] <= 0) {
                    this.cancel();
                    return;
                }
                InGameHud.fill(matrices, 0, 0, scaledWidth, scaledHeight, ColorHelper.Argb.getArgb(opacity[0], 255, 255, 255));
                opacity[0] -= opacityChangePerTick;
            }
        }, 0, (long)(1000.0 / 20));
    }

}
