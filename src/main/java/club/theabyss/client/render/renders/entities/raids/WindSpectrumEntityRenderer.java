package club.theabyss.client.render.renders.entities.raids;

import club.theabyss.client.render.models.entities.raids.WindSpectrumEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.raids.WindSpectrumEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class WindSpectrumEntityRenderer extends BipedEntityRenderer<WindSpectrumEntity, WindSpectrumEntityModel<WindSpectrumEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/wind_spectrum.png");
    private static final Identifier TEXTURE_CHARGING = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/charging/wind_spectrum_charging.png");

    public WindSpectrumEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new WindSpectrumEntityModel<>(context.getPart(EntityModelLayers.VEX)), 0.3F);
    }

    @Override
    public Identifier getTexture(WindSpectrumEntity entity) {
        return entity.isCharging() ? TEXTURE_CHARGING : TEXTURE;
    }

    @Override
    protected void scale(WindSpectrumEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4F, 0.4F, 0.4F);
    }

}
