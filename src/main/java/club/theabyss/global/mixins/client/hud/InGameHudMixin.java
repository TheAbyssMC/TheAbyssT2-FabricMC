package club.theabyss.global.mixins.client.hud;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Shadow private int scaledWidth;

    @Shadow private int scaledHeight;

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnHead(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        if (!client.options.hudHidden) {
            FlashBangClientManager.render(new MatrixStack(), (int) FlashBangClientManager.getOpacity(), scaledWidth, scaledHeight);
            FlashBangClientManager.renderStaticFrame(FlashBangClientManager.getFramebuffer(), FlashBangClientManager.getOpacity(), client.getWindow().getWidth(), client.getWindow().getHeight());
        }
    }

}
