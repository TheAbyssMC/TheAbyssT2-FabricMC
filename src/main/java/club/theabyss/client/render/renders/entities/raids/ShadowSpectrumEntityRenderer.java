package club.theabyss.client.render.renders.entities.raids;

import club.theabyss.client.render.models.entities.raids.ShadowSpectrumEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.raids.ShadowSpectrumEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class ShadowSpectrumEntityRenderer extends BipedEntityRenderer<ShadowSpectrumEntity, ShadowSpectrumEntityModel<ShadowSpectrumEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/shadow_spectrum.png");
    private static final Identifier TEXTURE_CHARGING = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/charging/shadow_spectrum_charging.png");

    public ShadowSpectrumEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new ShadowSpectrumEntityModel<>(context.getPart(EntityModelLayers.VEX)), 0.3F);
    }

    @Override
    public Identifier getTexture(ShadowSpectrumEntity entity) {
        return entity.isCharging() ? TEXTURE_CHARGING : TEXTURE;
    }

    @Override
    protected void scale(ShadowSpectrumEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4F, 0.4F, 0.4F);
    }

}
