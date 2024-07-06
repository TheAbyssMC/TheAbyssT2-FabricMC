package club.theabyss.client.render.renders.entities.raids;

import club.theabyss.client.render.models.entities.raids.BloodSpectrumEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.raids.BloodSpectrumEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class BloodSpectrumEntityRenderer extends BipedEntityRenderer<BloodSpectrumEntity, BloodSpectrumEntityModel<BloodSpectrumEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/blood_spectrum.png");
    private static final Identifier TEXTURE_CHARGING = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/charging/blood_spectrum_charging.png");

    public BloodSpectrumEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new BloodSpectrumEntityModel<>(context.getPart(EntityModelLayers.VEX)), 0.3f);
    }

    @Override
    public Identifier getTexture(BloodSpectrumEntity entity) {
        return entity.isCharging() ? TEXTURE_CHARGING : TEXTURE;
    }

    @Override
    protected void scale(BloodSpectrumEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4F, 0.4F, 0.4F);
    }


}
