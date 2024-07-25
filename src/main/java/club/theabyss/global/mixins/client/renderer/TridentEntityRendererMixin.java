package club.theabyss.global.mixins.client.renderer;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import club.theabyss.global.utils.TheAbyssConstants;
import club.theabyss.server.game.item.items.TheAbyssItems;
import net.minecraft.client.render.entity.TridentEntityRenderer;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TridentEntityRenderer.class)
public class TridentEntityRendererMixin {

    @Inject(method = "getTexture(Lnet/minecraft/entity/projectile/TridentEntity;)Lnet/minecraft/util/Identifier;", at = @At(value = "HEAD"), cancellable = true)
    public void getTextureMixin(TridentEntity entity, CallbackInfoReturnable<Identifier> cir) {
        if(((ITridentEntity)entity).getTridentStack$0().isOf(TheAbyssItems.TWILIGHT_TRIDENT)) {
            cir.setReturnValue(new Identifier(TheAbyssConstants.MOD_ID,"textures/entity/twilight_trident.png"));
        }
    }

}
