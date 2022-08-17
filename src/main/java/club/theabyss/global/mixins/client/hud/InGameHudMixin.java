package club.theabyss.global.mixins.client.hud;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Inject(method = "render", at = @At("TAIL"))
    private void renderInjection(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        FlashBangClientManager.render(matrices, (int) FlashBangClientManager.getOpacity(), scaledWidth, scaledHeight);
    }

}
