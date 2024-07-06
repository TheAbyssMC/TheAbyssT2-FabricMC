package club.theabyss.client.render.renders.entities.raids;

import club.theabyss.client.render.models.entities.raids.FireSpectrumEntityModel;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.entity.entities.raids.FireSpectrumEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class FireSpectrumEntityRenderer extends BipedEntityRenderer<FireSpectrumEntity, FireSpectrumEntityModel<FireSpectrumEntity>> {

    private static final Identifier TEXTURE = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/fire_spectrum.png");
    private static final Identifier TEXTURE_CHARGING = new Identifier(TheAbyssConstants.MOD_ID, "entities/textures/raids/elemental_spectrums/charging/fire_spectrum_charging.png");

    public FireSpectrumEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new FireSpectrumEntityModel<>(context.getPart(EntityModelLayers.VEX)), 0.3F);
    }

    @Override
    public Identifier getTexture(FireSpectrumEntity entity) {
        return entity.isCharging() ? TEXTURE_CHARGING : TEXTURE;
    }

    @Override
    protected void scale(FireSpectrumEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.4F, 0.4F, 0.4F);
    }

}
