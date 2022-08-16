package club.theabyss.client.render.models.entities.watercreatures;

import club.theabyss.server.game.entity.entities.watercreatures.KrakenEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

import java.util.Arrays;

public class KrakenEntityModel extends EntityModel<KrakenEntity> {

	private final ModelPart[] tentacles = new ModelPart[8];

	private final ModelPart body;

	private final ModelPart tentacle0;

	private final ModelPart tentacle1;

	private final ModelPart tentacle2;

	private final ModelPart tentacle3;

	private final ModelPart tentacle4;

	private final ModelPart tentacle5;

	private final ModelPart tentacle6;

	private final ModelPart tentacle7;

	public KrakenEntityModel(ModelPart root) {
		this.body = root.getChild("body");
		this.tentacle0 = root.getChild("tentacle0");
		this.tentacle1 = root.getChild("tentacle1");
		this.tentacle2 = root.getChild("tentacle2");
		this.tentacle3 = root.getChild("tentacle3");
		this.tentacle4 = root.getChild("tentacle4");
		this.tentacle5 = root.getChild("tentacle5");
		this.tentacle6 = root.getChild("tentacle6");
		this.tentacle7 = root.getChild("tentacle7");

		Arrays.setAll(this.tentacles, index -> root.getChild(getTentacleName(index)));
	}

	private static String getTentacleName(int index) {
		return "tentacle" + index;
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(80, 36).mirrored().cuboid(-6.0F, -8.0F, -6.0F, 12.0F, 16.0F, 12.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		body.addChild("body_sub_0", ModelPartBuilder.create().uv(88, 0).mirrored().cuboid(-5.0F, 1.0F, -5.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F)).mirrored(false)
		.uv(70, 43).mirrored().cuboid(-0.5F, 8.0F, -0.5F, 1.0F, 15.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
		.uv(56, 53).mirrored().cuboid(-1.5F, 21.0F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(49, 0).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 9.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		head.addChild("tongue1", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue2", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue3", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue4", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue5", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue6", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue7", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		head.addChild("tongue8", ModelPartBuilder.create().uv(48, 40).cuboid(-0.5F, -3.5F, -0.5F, 1.0F, 7.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData front = head.addChild("front", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.0F, 0.0F));

		front.addChild("t_left", ModelPartBuilder.create().uv(24, 28).cuboid(-4.0F, -22.0F, -2.0F, 6.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, -4.0F));

		front.addChild("t_right", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -22.0F, -2.0F, 6.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, -4.0F));

		front.addChild("b_left", ModelPartBuilder.create().uv(0, 28).cuboid(-4.0F, -22.0F, -4.0F, 6.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(4.0F, 0.0F, 4.0F));

		front.addChild("b_right", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, -22.0F, -4.0F, 6.0F, 22.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 4.0F));

		ModelPartData back = head.addChild("back", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 4.0F, 0.0F));

		back.addChild("top", ModelPartBuilder.create().uv(48, 16).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, -4.0F, -0.1745F, 0.0F, 0.0F));

		back.addChild("bottom", ModelPartBuilder.create().uv(48, 28).cuboid(-4.0F, 0.0F, 0.0F, 8.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 4.0F, 0.1745F, 0.0F, 0.0F));

		back.addChild("left", ModelPartBuilder.create().uv(64, 20).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(4.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.1745F));

		back.addChild("right", ModelPartBuilder.create().uv(64, 8).cuboid(0.0F, 0.0F, -4.0F, 0.0F, 12.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(-4.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.1745F));

		modelPartData.addChild("tentacle0", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-5.0F, 31.0F, 0.0F));

		modelPartData.addChild("tentacle1", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.5F, 31.0F, -3.5F));

		modelPartData.addChild("tentacle2", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 31.0F, -5.0F));

		modelPartData.addChild("tentacle3", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.5F, 31.0F, -3.5F));

		modelPartData.addChild("tentacle4", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(14.0F, 0.0F, -6.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-10.0F, 31.0F, -5.0F, 0.0F, 1.5708F, 0.0F));

		modelPartData.addChild("tentacle5", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.5F, 31.0F, 3.5F));

		modelPartData.addChild("tentacle6", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -4.35F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, 31.0F, 1.65F, 0.0F, 3.1416F, 0.0F));

		modelPartData.addChild("tentacle7", ModelPartBuilder.create().uv(120, 44).mirrored().cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 18.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-3.5F, 31.0F, 3.5F));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void setAngles(KrakenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		for (ModelPart modelPart : this.tentacles) {
			modelPart.pitch = ageInTicks;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle0.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle1.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle2.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle3.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle4.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle5.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle6.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tentacle7.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

}