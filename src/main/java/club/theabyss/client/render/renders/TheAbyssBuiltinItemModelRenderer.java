package club.theabyss.client.render.renders;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.entity.model.TridentEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

public class TheAbyssBuiltinItemModelRenderer implements SimpleSynchronousResourceReloadListener {

    private final EntityModelLoader entityModelLoader;

    private TridentEntityModel modelTrident;

    public TheAbyssBuiltinItemModelRenderer(EntityModelLoader entityModelLoader) {
        this.entityModelLoader = entityModelLoader;
    }

    public void reload(ResourceManager manager) {
        this.modelTrident = new TridentEntityModel(this.entityModelLoader.getModelPart(EntityModelLayers.TRIDENT));
    }

    public void render(ItemStack itemStack, ModelTransformation.Mode transformType, MatrixStack matrices, VertexConsumerProvider vertices, int light, int overlay) {
        if (itemStack.isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            renderTrident(modelTrident, itemStack, transformType, matrices, vertices, light, overlay);
        }
    }

    public static void renderTrident(TridentEntityModel model, ItemStack stack, ModelTransformation.Mode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.scale(1.0F, -1.0F, -1.0F);
        VertexConsumer vertexConsumer2 = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers, model.getLayer(new Identifier(TheAbyssConstants.MOD_ID, "textures/entity/twilight_trident.png")), false, stack.hasGlint());
        model.render(matrices, vertexConsumer2, light, overlay, 1.0F, 1.0F, 1.0F, 1.0F);
        matrices.pop();
    }

    @Override
    public Identifier getFabricId() {
        return new Identifier(TheAbyssConstants.MOD_ID, "theabyss2_builtin_item_model_renderer");
    }

}
