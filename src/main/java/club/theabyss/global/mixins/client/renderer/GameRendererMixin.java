package club.theabyss.global.mixins.client.renderer;

import club.theabyss.client.render.flashBang.FlashBangClientManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Shadow @Final private MinecraftClient client;

    @Inject(method = "render", at = @At("TAIL"))
    private void injectOnRender(float tickDelta, long startTime, boolean tick, CallbackInfo ci) {
        if (client.options.hudHidden)
            FlashBangClientManager.render(new MatrixStack(), (int) FlashBangClientManager.getOpacity(), client.getWindow().getScaledWidth(), client.getWindow().getScaledHeight());
    }

}
