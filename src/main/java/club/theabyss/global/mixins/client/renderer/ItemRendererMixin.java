package club.theabyss.global.mixins.client.renderer;

import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/model/BakedModelManager;getModel(Lnet/minecraft/client/util/ModelIdentifier;)Lnet/minecraft/client/render/model/BakedModel;"),
            index = 8, argsOnly = true)
    public BakedModel useTwilightTridentModel(BakedModel value, ItemStack stack) {
        if (stack.isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            return ((ItemRendererAccessor) this).theabyss2$getModels().getModelManager().getModel(new ModelIdentifier(TheAbyssConstants.MOD_ID, "netherite_trident", "inventory"));
        }
        return value;
    }

    @ModifyVariable(method = "getModel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/item/ItemModels;getModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/render/model/BakedModel;", shift = At.Shift.BY, by = 2), index = 5)
    public BakedModel getHeldTwilightTridentModel(BakedModel value, ItemStack stack) {
        if (stack.isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            return ((ItemRendererAccessor) this).theabyss2$getModels().getModelManager().getModel(new ModelIdentifier(TheAbyssConstants.MOD_ID, "twilight_trident_in_hand", "inventory"));
        }
        return value;
    }

}
