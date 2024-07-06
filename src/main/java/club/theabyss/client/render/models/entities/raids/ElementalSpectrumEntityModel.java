package club.theabyss.client.render.models.entities.raids;

import club.theabyss.server.game.entity.entities.raids.ElementalSpectrumEntity;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;

public class ElementalSpectrumEntityModel<T extends ElementalSpectrumEntity> extends BipedEntityModel<T> {

    private final ModelPart leftWing;
    private final ModelPart rightWing;

    public ElementalSpectrumEntityModel(ModelPart modelPart) {
        super(modelPart);
        this.leftLeg.visible = false;
        this.hat.visible = false;
        this.rightWing = modelPart.getChild("right_wing");
        this.leftWing = modelPart.getChild("left_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(32, 0).cuboid(-1.0F, -1.0F, -2.0F, 6.0F, 10.0F, 4.0F), ModelTransform.pivot(-1.9F, 12.0F, 0.0F));
        modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(0, 32).cuboid(-20.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F), ModelTransform.NONE);
        modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, 0.0F, 0.0F, 20.0F, 12.0F, 1.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    protected Iterable<ModelPart> getBodyParts() {
        return Iterables.concat(super.getBodyParts(), ImmutableList.of(this.rightWing, this.leftWing));
    }

    public void setAngles(T elementalSpectrum, float f, float g, float h, float i, float j) {
        super.setAngles(elementalSpectrum, f, g, h, i, j);
        if (elementalSpectrum.isCharging()) {
            if (elementalSpectrum.getMainHandStack().isEmpty()) {
                this.rightArm.pitch = 4.712389F;
                this.leftArm.pitch = 4.712389F;
            } else if (elementalSpectrum.getMainArm() == Arm.RIGHT) {
                this.rightArm.pitch = 3.7699115F;
            } else {
                this.leftArm.pitch = 3.7699115F;
            }
        }

        this.rightLeg.pitch += 0.62831855F;
        this.rightWing.pivotZ = 2.0F;
        this.leftWing.pivotZ = 2.0F;
        this.rightWing.pivotY = 1.0F;
        this.leftWing.pivotY = 1.0F;
        this.rightWing.yaw = 0.47123894F + MathHelper.cos(h * 45.836624F * 0.017453292F) * 3.1415927F * 0.05F;
        this.leftWing.yaw = -this.rightWing.yaw;
        this.leftWing.roll = -0.47123894F;
        this.leftWing.pitch = 0.47123894F;
        this.rightWing.pitch = 0.47123894F;
        this.rightWing.roll = 0.47123894F;
    }

}
