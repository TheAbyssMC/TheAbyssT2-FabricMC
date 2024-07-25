package club.theabyss.global.mixins.client.renderer;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import club.theabyss.global.utils.TheAbyssConstants;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onEntitySpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;onSpawnPacket(Lnet/minecraft/network/packet/s2c/play/EntitySpawnS2CPacket;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void onEntitySpawnMixin(EntitySpawnS2CPacket packet, CallbackInfo ci, EntityType<?> entityType, Entity entity) {
        if (entityType == EntityType.TRIDENT) {
            ((ITridentEntity) ((TridentEntity) entity)).setTridentStack$0(new ItemStack(Registry.ITEM.get(new Identifier(TheAbyssConstants.MOD_ID, "twilight_trident"))));
        }
    }

}
