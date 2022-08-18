package club.theabyss.client.render.flashBang;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;

public class FlashBangClientManager {

    private @Getter @Setter static boolean shouldTick = false;
    private @Getter @Setter static float opacity = 0f;
    private @Getter @Setter static int flashSeconds = 0;
    private @Getter @Setter static int opaqueTicks = 0;

    public static void reset() {
        shouldTick = false;
        opacity = 0;
        flashSeconds = 0;
        opaqueTicks = 0;
    }

    public static void tick() {
        if (shouldTick) {
            if (opaqueTicks <= 0) {
                opacity -= 255f / (flashSeconds * 20);
            }
            opacity = MathHelper.clamp(opacity, 0, 255);
            if (opacity <= 0) {
                shouldTick = false;
            }
            if (opaqueTicks > 0) opaqueTicks--;
        }
    }

    public static void render(MatrixStack matrices, int opacity, int width, int height) {
        if (opacity > 0 & shouldTick) DrawableHelper.fill(matrices, 0, 0, width, height, ColorHelper.Argb.getArgb(opacity, 255, 255, 255));
    }

}
