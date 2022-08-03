package club.theabyss.global.mixins.server.entites;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.utils.GlobalGameManager;
import club.theabyss.server.game.entity.events.player.ServerPlayerEntityEvents;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void modifyLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!(GlobalGameManager.getNowDay() >= 7)) return;

        var entity = ((LivingEntity)(Object)this);
        var entityType = ((LivingEntity)(Object)this).getType();

        if (PassiveEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass()) || FishEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass())) {
            if (entityType.equals(EntityType.TURTLE) || entityType.equals(EntityType.SQUID)) return;
            ci.cancel();
        }
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    private void triggerTotemEvent(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (TheAbyssManager.getInstance().serverManager() == null) return;

        var totemManager = TheAbyssManager.getInstance().serverGameManager().totemManager();
        var serverPlayer = (ServerPlayerEntity)(Object) this;
        var playerName = serverPlayer.getName();
        var uuid = serverPlayer.getUuid();

        var usedTotemsMap = totemManager.totemData.getUsedTotemsMap();

        usedTotemsMap.put(uuid, usedTotemsMap.containsKey(uuid) ? usedTotemsMap.get(uuid) + 1 : 1);

        TheAbyssManager.getInstance().serverManager().minecraftServer().getPlayerManager().getPlayerList().forEach(p -> p.sendMessage((ChatFormatter.stringFormatWithPrefixToText("&7El jugador &6" + playerName.asString() + "&7 ha gastado su totem numero &6" + usedTotemsMap.get(uuid) + "&7.")), false));

        ServerPlayerEntityEvents.PlayerResurrect.EVENT.invoker().resurrect(serverPlayer);
    }

}
