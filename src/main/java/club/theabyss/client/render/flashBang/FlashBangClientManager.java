package club.theabyss.client.render.flashBang;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class FlashBangClientManager {

    private @Getter @Setter static boolean shouldTick = false;
    private @Getter @Setter static float opacity = 0f;
    private @Getter @Setter static int flashSeconds = 0;
    private @Getter @Setter static int opaqueTicks = 0;
    private @Getter @Setter static Framebuffer framebuffer;

    public static void reset() {
        shouldTick = false;
        opacity = 0;
        flashSeconds = 0;
        opaqueTicks = 0;
        framebuffer = null;
    }

    public static void tick() {
        if (shouldTick) {
            if (opaqueTicks <= 0) {
                opacity -= 255f / (flashSeconds * 20);
            }
            opacity = MathHelper.clamp(opacity, 0, 255);
            if (opacity <= 0) {
                reset();
                shouldTick = false;
            }
            if (opaqueTicks > 0) opaqueTicks--;
        }
    }

    public static Framebuffer copyColorsFrom(Framebuffer original, Framebuffer copy) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, original.fbo);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, copy.fbo);
        GlStateManager._glBlitFrameBuffer(0, 0, original.textureWidth, original.textureHeight, 0, 0, copy.textureWidth, copy.textureHeight, GlConst.GL_COLOR_BUFFER_BIT, GlConst.GL_NEAREST);
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);
        return copy;
    }

    public static void render(MatrixStack matrices, int opacity, int width, int height) {
        if (opacity > 0 && shouldTick) DrawableHelper.fill(matrices, 0, 0, width, height, ColorHelper.Argb.getArgb(opacity, 255, 255, 255));
    }

    public static void renderStaticFrame(Framebuffer framebuffer, float opacity, int width, int height) {
        if (opacity > 0 && opaqueTicks <= 0 && shouldTick && framebuffer != null) {
            float scaledOpacity = opacity / (255f * 3);
            RenderSystem.backupProjectionMatrix();

            var shaderColorModulator = MinecraftClient.getInstance().gameRenderer.blitScreenShader.colorModulator;
            if (shaderColorModulator == null) throw new IllegalStateException("This function must never be called without a color modulator instance.");

            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            shaderColorModulator.set(1.0f, 1.0f, 1.0f, scaledOpacity);
            framebuffer.draw(width, height, false);
            RenderSystem.disableBlend();

            RenderSystem.restoreProjectionMatrix();
        }
    }

}
