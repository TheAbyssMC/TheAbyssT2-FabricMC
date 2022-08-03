package club.theabyss.client.render.models.entities.spiders;

import club.theabyss.server.game.entity.entities.spiders.WeaverSpiderEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class WeaverSpiderModel extends EntityModel<WeaverSpiderEntity> {
    private final ModelPart head;
    private final ModelPart neck;
    private final ModelPart body;
    private final ModelPart leg1;
    private final ModelPart leg2;
    private final ModelPart leg3;
    private final ModelPart leg4;
    private final ModelPart leg5;
    private final ModelPart leg6;
    private final ModelPart leg7;
    private final ModelPart leg8;

    public WeaverSpiderModel(ModelPart root) {
        this.head = root.getChild("head");
        this.neck = root.getChild("neck");
        this.body = root.getChild("body");
        this.leg1 = root.getChild("leg1");
        this.leg2 = root.getChild("leg2");
        this.leg3 = root.getChild("leg3");
        this.leg4 = root.getChild("leg4");
        this.leg5 = root.getChild("leg5");
        this.leg6 = root.getChild("leg6");
        this.leg7 = root.getChild("leg7");
        this.leg8 = root.getChild("leg8");
    }

    public static TexturedModelData getTexturedModelData() {

        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(32, 6).cuboid(-4.0F, -2.0F, -8.0F, 8.0F, 3.0F, 8.0F, new Dilation(0.0F))
                .uv(33, 5).cuboid(-4.0F, -3.0F, -7.0F, 8.0F, 1.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, -3.0F));

        ModelPartData headChin = head.addChild("headChin", ModelPartBuilder.create().uv(7, 23).cuboid(-4.0F, -8.0F, -8.0F, 8.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(9, 20).cuboid(-3.5F, -8.5F, -10.9F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(75, 31).cuboid(-4.0F, -8.0F, -8.0F, 8.0F, 3.0F, 5.0F, new Dilation(0.0F))
                .uv(81, 39).cuboid(-3.5F, -8.5F, -10.9F, 7.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(83, 45).cuboid(-3.5F, -8.5F, -12.9F, 7.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 9.0F, 3.0F));

        ModelPartData mandible = head.addChild("mandible", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 9.0F, 3.0F));

        ModelPartData mandibleFront = mandible.addChild("mandibleFront", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mandible1 = mandibleFront.addChild("mandible1", ModelPartBuilder.create().uv(48, 21).cuboid(-3.1F, -7.75F, -12.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, -1.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData mandibleTip = mandible1.addChild("mandibleTip", ModelPartBuilder.create().uv(48, 25).cuboid(-2.1F, -4.25F, -13.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -5.0F, 2.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData mandible2 = mandibleFront.addChild("mandible2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.0F, -1.0F, -0.1309F, 0.0F, 0.0F));

        ModelPartData head_sub_3 = mandible2.addChild("head_sub_3", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(1.1F, -7.75F, -12.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mandibleTip2 = mandible2.addChild("mandibleTip2", ModelPartBuilder.create(), ModelTransform.of(0.0F, -5.0F, 2.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData head_sub_5 = mandibleTip2.addChild("head_sub_5", ModelPartBuilder.create().uv(48, 25).mirrored().cuboid(2.1F, -4.25F, -13.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mandibleBack = mandible.addChild("mandibleBack", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -20.0F));

        ModelPartData mandible3 = mandibleBack.addChild("mandible3", ModelPartBuilder.create().uv(48, 21).cuboid(-2.1F, -7.75F, 10.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData mandibleTip1 = mandible3.addChild("mandibleTip1", ModelPartBuilder.create().uv(48, 25).cuboid(-1.1F, -3.5F, 10.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.0F, 4.0F, -1.1345F, 0.0F, 0.0F));

        ModelPartData head_sub_24 = mandibleTip1.addChild("head_sub_24", ModelPartBuilder.create().uv(48, 25).mirrored().cuboid(1.1F, -3.5F, 10.5F, 0.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData mandible4 = mandibleBack.addChild("mandible4", ModelPartBuilder.create(), ModelTransform.of(0.0F, 1.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData head_sub_2 = mandible4.addChild("head_sub_2", ModelPartBuilder.create().uv(48, 21).mirrored().cuboid(0.1F, -7.75F, 10.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData neck = modelPartData.addChild("neck", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.99F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F))
                .uv(18, 6).cuboid(-2.0F, -3.0F, -3.0F, 4.0F, 0.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(-1.0F, -5.0F, -4.0F, 0.0F, 3.0F, 6.0F, new Dilation(0.0F))
                .uv(0, 14).cuboid(1.0F, -5.0F, -4.0F, 0.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 15.0F, 0.0F));

        ModelPartData bodyBase = neck.addChild("bodyBase", ModelPartBuilder.create().uv(0, 4).cuboid(-3.51F, -15.0F, -1.0F, 7.0F, 3.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 9.0F, 4.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(1, 12).cuboid(-5.0F, -6.0F, -6.0F, 10.0F, 9.0F, 11.0F, new Dilation(0.0F))
                .uv(10, 21).cuboid(-5.0F, -5.0F, 5.0F, 10.0F, 7.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.0F, 9.0F, 0.1309F, 0.0F, 0.0F));

        ModelPartData leg1 = modelPartData.addChild("leg1", ModelPartBuilder.create().uv(18, 0).cuboid(-17.0F, -1.0F, -1.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 15.0F, 5.0F, 0.0F, 0.3491F, 1.2217F));

        ModelPartData leg1mid = leg1.addChild("leg1mid", ModelPartBuilder.create().uv(19, 1).cuboid(-20.0F, -12.0F, 5.75F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, -6.0F, 0.0F, 0.0F, -0.829F));

        ModelPartData leg1tip = leg1.addChild("leg1tip", ModelPartBuilder.create().uv(19, 1).cuboid(-11.8F, -22.0F, 7.8F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -8.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData leg2 = modelPartData.addChild("leg2", ModelPartBuilder.create(), ModelTransform.of(0.0F, 15.0F, 5.0F, 0.0F, -0.3491F, -1.2217F));

        ModelPartData leg2_sub_0 = leg2.addChild("leg2_sub_0", ModelPartBuilder.create().uv(18, 0).mirrored().cuboid(3.0F, -7.0F, 3.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, 6.0F, -4.0F));

        ModelPartData leg2mid = leg2.addChild("leg2mid", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.0F, -6.0F, 0.0F, 0.0F, 0.829F));

        ModelPartData leg2_sub_2 = leg2mid.addChild("leg2_sub_2", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(12.0F, -12.0F, 5.75F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg2tip = leg2.addChild("leg2tip", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, -8.0F, 0.0F, 0.0F, 1.5708F));

        ModelPartData leg2_sub_4 = leg2tip.addChild("leg2_sub_4", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(5.8F, -22.0F, 7.8F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg3 = modelPartData.addChild("leg3", ModelPartBuilder.create().uv(18, 0).cuboid(-19.0F, -2.5F, -0.25F, 12.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-1.0F, 17.0F, 1.0F, 0.0F, 0.1745F, 1.2217F));

        ModelPartData leg3mid = leg3.addChild("leg3mid", ModelPartBuilder.create().uv(19, 1).cuboid(-23.5F, -14.5F, -4.75F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 5.0F, 0.0F, 0.0F, -0.8727F));

        ModelPartData leg3tip = leg3.addChild("leg3tip", ModelPartBuilder.create().uv(19, 1).cuboid(-11.8F, -27.5F, -5.25F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 3.0F, 6.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData leg3tip2 = leg3tip.addChild("leg3tip2", ModelPartBuilder.create(), ModelTransform.of(-28.0F, -5.0F, -1.0F, 0.0F, 0.0F, -0.2182F));

        ModelPartData leg4 = modelPartData.addChild("leg4", ModelPartBuilder.create(), ModelTransform.of(1.0F, 17.0F, 1.0F, 0.0F, -0.1745F, -1.2217F));

        ModelPartData leg4_sub_0 = leg4.addChild("leg4_sub_0", ModelPartBuilder.create().uv(18, 0).mirrored().cuboid(3.0F, -9.5F, 0.75F, 12.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, 7.0F, -1.0F));

        ModelPartData leg4mid = leg4.addChild("leg4mid", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.0F, 5.0F, 0.0F, 0.0F, 0.8727F));

        ModelPartData leg4_sub_2 = leg4mid.addChild("leg4_sub_2", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(12.5F, -14.5F, -4.75F, 11.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg4tip = leg4.addChild("leg4tip", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 3.0F, 6.0F, 0.0F, 0.0F, 1.5708F));

        ModelPartData leg4_sub_4 = leg4tip.addChild("leg4_sub_4", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(3.8F, -27.5F, -5.25F, 8.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg4tip2 = leg4tip.addChild("leg4tip2", ModelPartBuilder.create(), ModelTransform.of(28.0F, -5.0F, -1.0F, 0.0F, 0.0F, 0.2182F));

        ModelPartData leg4_sub_6 = leg4tip2.addChild("leg4_sub_6", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg5 = modelPartData.addChild("leg5", ModelPartBuilder.create().uv(18, 0).cuboid(-20.0F, -2.0F, -2.5F, 13.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(1.0F, 18.0F, 0.0F, 0.0F, 0.1745F, 1.2217F));

        ModelPartData leg5mid = leg5.addChild("leg5mid", ModelPartBuilder.create().uv(19, 1).cuboid(-23.5F, -14.5F, -7.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 5.0F, 0.0F, 0.0F, -0.829F));

        ModelPartData leg5tip = leg5.addChild("leg5tip", ModelPartBuilder.create().uv(19, 1).cuboid(-12.5F, -26.6F, -9.5F, 7.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData leg6 = modelPartData.addChild("leg6", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 18.0F, 0.0F, 0.0F, -0.1745F, -1.2217F));

        ModelPartData leg6_sub_0 = leg6.addChild("leg6_sub_0", ModelPartBuilder.create().uv(18, 0).mirrored().cuboid(3.0F, -9.0F, -2.5F, 13.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, 7.0F, 0.0F));

        ModelPartData leg6mid = leg6.addChild("leg6mid", ModelPartBuilder.create(), ModelTransform.of(0.0F, -2.0F, 5.0F, 0.0F, 0.0F, 0.829F));

        ModelPartData leg6_sub_2 = leg6mid.addChild("leg6_sub_2", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(13.5F, -14.5F, -7.0F, 10.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg6tip = leg6.addChild("leg6tip", ModelPartBuilder.create(), ModelTransform.of(0.0F, 0.0F, 8.0F, 0.0F, 0.0F, 1.5708F));

        ModelPartData leg6_sub_4 = leg6tip.addChild("leg6_sub_4", ModelPartBuilder.create().uv(19, 1).mirrored().cuboid(5.5F, -26.6F, -9.5F, 7.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg7 = modelPartData.addChild("leg7", ModelPartBuilder.create().uv(24, 36).cuboid(-17.0F, -2.0F, -2.1F, 11.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 16.0F, -3.0F, 0.0F, 0.0873F, 1.2217F));

        ModelPartData leg7mid = leg7.addChild("leg7mid", ModelPartBuilder.create().uv(25, 37).cuboid(-20.5F, -12.45F, -6.75F, 9.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 5.0F, 0.0F, 0.0F, -0.7854F));

        ModelPartData leg7tip = leg7.addChild("leg7tip", ModelPartBuilder.create().uv(25, 37).cuboid(-13.9F, -23.0F, -7.25F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 6.0F, 0.0F, 0.0F, -1.5708F));

        ModelPartData leg8 = modelPartData.addChild("leg8", ModelPartBuilder.create(), ModelTransform.of(-8.0F, 16.0F, -3.0F, 0.0F, -0.0873F, -1.2217F));

        ModelPartData leg8_sub_0 = leg8.addChild("leg8_sub_0", ModelPartBuilder.create().uv(24, 36).mirrored().cuboid(2.0F, -9.0F, -6.1F, 11.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, 7.0F, 4.0F));

        ModelPartData leg8mid = leg8.addChild("leg8mid", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.0F, 5.0F, 0.0F, 0.0F, 0.7854F));

        ModelPartData leg8_sub_2 = leg8mid.addChild("leg8_sub_2", ModelPartBuilder.create().uv(25, 37).mirrored().cuboid(11.5F, -12.45F, -6.75F, 9.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leg8tip = leg8.addChild("leg8tip", ModelPartBuilder.create(), ModelTransform.of(0.0F, -1.0F, 6.0F, 0.0F, 0.0F, 1.5708F));

        ModelPartData leg8_sub_4 = leg8tip.addChild("leg8_sub_4", ModelPartBuilder.create().uv(25, 37).mirrored().cuboid(5.9F, -23.0F, -7.25F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        leg8.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(WeaverSpiderEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
        float f = 0.7853982F;
        this.leg1.roll = -0.7853982F;
        this.leg2.roll = 0.7853982F;
        this.leg3.roll = -0.58119464F;
        this.leg4.roll = 0.58119464F;
        this.leg5.roll = -0.58119464F;
        this.leg6.roll = 0.58119464F;
        this.leg7.roll = -0.7853982F;
        this.leg8.roll = 0.7853982F;
        float g = -0.0F;
        float h = 0.3926991F;
        this.leg1.yaw = 0.7853982F;
        this.leg2.yaw = -0.7853982F;
        this.leg3.yaw = 0.3926991F;
        this.leg4.yaw = -0.3926991F;
        this.leg5.yaw = -0.3926991F;
        this.leg6.yaw = 0.3926991F;
        this.leg7.yaw = -0.7853982F;
        this.leg8.yaw = 0.7853982F;
        float i = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 0.0F) * 0.4F) * limbDistance;
        float j = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 3.1415927F) * 0.4F) * limbDistance;
        float k = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 1.5707964F) * 0.4F) * limbDistance;
        float l = -(MathHelper.cos(limbAngle * 0.6662F * 2.0F + 4.712389F) * 0.4F) * limbDistance;
        float m = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 0.0F) * 0.4F) * limbDistance;
        float n = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 3.1415927F) * 0.4F) * limbDistance;
        float o = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 1.5707964F) * 0.4F) * limbDistance;
        float p = Math.abs(MathHelper.sin(limbAngle * 0.6662F + 4.712389F) * 0.4F) * limbDistance;
        ModelPart var10000 = this.leg1;
        var10000.yaw += i;
        var10000 = this.leg2;
        var10000.yaw -= i;
        var10000 = this.leg3;
        var10000.yaw += j;
        var10000 = this.leg4;
        var10000.yaw -= j;
        var10000 = this.leg5;
        var10000.yaw += k;
        var10000 = this.leg6;
        var10000.yaw -= k;
        var10000 = this.leg7;
        var10000.yaw += l;
        var10000 = this.leg8;
        var10000.yaw -= l;
        var10000 = this.leg1;
        var10000.roll += m;
        var10000 = this.leg2;
        var10000.roll -= m;
        var10000 = this.leg3;
        var10000.roll += n;
        var10000 = this.leg4;
        var10000.roll -= n;
        var10000 = this.leg5;
        var10000.roll += o;
        var10000 = this.leg6;
        var10000.roll -= o;
        var10000 = this.leg7;
        var10000.roll += p;
        var10000 = this.leg8;
        var10000.roll -= p;
    }

}
