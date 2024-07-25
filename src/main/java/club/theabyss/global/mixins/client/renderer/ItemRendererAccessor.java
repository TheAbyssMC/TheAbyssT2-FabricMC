package club.theabyss.global.mixins.client.renderer;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.item.ItemModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ItemRenderer.class)
public interface ItemRendererAccessor {

    @Accessor("models")
    ItemModels theabyss2$getModels();

    @Invoker("renderBakedItemModel")
    void theabyss2$renderBakedItemModel(BakedModel model, ItemStack stack, int light, int overlay, MatrixStack matrices, VertexConsumer vertexConsumer4);

}
