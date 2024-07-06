package club.theabyss.global.mixins.server.entites;

import club.theabyss.TheAbyss;
import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.game.entity.events.player.ServerPlayerEntityEvents;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    @Shadow private @Nullable LivingEntity attacker;

    @Inject(method = "dropLoot", at = @At("HEAD"), cancellable = true)
    private void modifyLoot(DamageSource source, boolean causedByPlayer, CallbackInfo ci) {
        if (!(GlobalDataAccess.getNowDay() >= 7)) return;

        var entity = ((LivingEntity)(Object)this);
        var entityType = ((LivingEntity)(Object)this).getType();

        if (PassiveEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass()) || FishEntity.class.isAssignableFrom(Objects.requireNonNull(entityType.downcast(entity)).getClass())) {
            if (entityType.equals(EntityType.TURTLE) || entityType.equals(EntityType.SQUID)) return;
            ci.cancel();
        }
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/stat/Stat;)V"))
    private void triggerTotemEvent(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (TheAbyss.getInstance().serverManager() == null) return;

        var totemManager = TheAbyss.getInstance().serverGameManager().totemManager();
        var serverPlayer = (ServerPlayerEntity)(Object) this;
        var playerName = serverPlayer.getName();
        var uuid = serverPlayer.getUuid();

        var damageCauseName = getDamageCauseName(source, "&8");

        var usedTotemsMap = totemManager.totemData.getUsedTotemsMap();

        usedTotemsMap.put(uuid, usedTotemsMap.containsKey(uuid) ? usedTotemsMap.get(uuid) + 1 : 1);

        TheAbyss.getInstance().serverManager().minecraftServer().getPlayerManager().getPlayerList().forEach(p -> p.sendMessage((ChatFormatter.stringFormatWithPrefixToText("&7El jugador &6" + playerName.asString() + "&7 ha gastado su totem numero &6" + usedTotemsMap.get(uuid) + "&7." + " &8Causa: " + damageCauseName + "&8.")), false));

        ServerPlayerEntityEvents.PlayerResurrect.EVENT.invoker().resurrect(serverPlayer);
    }

    @Unique
    private String getDamageCauseName(DamageSource damageSource, String colorChars) {
        return switch(damageSource.getName()) {
            case "inFire", "onFire" -> "Fuego";
            case "lightningBolt" -> "Rayo";
            case "lava" -> "Lava";
            case "hotFloor" -> "Suelo incandescente.";
            case "inWall" -> "Sofocación";
            case "cramming" -> "Cramming";
            case "drown" -> "Ahogamiento";
            case "starve" -> "Hambre";
            case "cactus" -> "Cactus";
            case "fall" -> "Caída";
            case "flyIntoWall" -> "Energía cinética";
            case "outOfWorld" -> "Vacío";
            case "generic" -> "Genérico";
            case "magic" -> "Magia";
            case "wither" -> "Wither";
            case "anvil" -> "Yunque";
            case "fallingBlock" -> "Falling Block";
            case "dragonBreath" -> "Aliento de dragón";
            case "dryOut" -> "Secado";
            case "sweetBerryBush" -> "Bayas dulces";
            case "freeze" -> "Congelación";
            case "fallingStalactite" -> "Estalactita";
            case "stalagmite" -> "Estalagmita";
            case "explosion" -> "Explosión";
            case "badRespawnPoint" -> "Respawn Anchor";
            case "mob", "player" -> {
                var attacker = damageSource.getAttacker();
                if (attacker == null) {
                    yield "Ataque de entidad";
                } else {
                    if (!damageSource.isProjectile()) {
                        yield "Ataque de entidad (" + attacker.getName().getString() + colorChars + ")";
                    } else {
                        var source = damageSource.getSource();
                        assert source != null;
                        yield "Proyectil (" + source.getName().getString() + colorChars + ")";
                    }
                }
            }
            case "sting" -> {
                var mob = damageSource.getAttacker();
                assert mob != null;
                yield "Picadura (" + mob.getName().getString() + colorChars + ")";
            }
            case "arrow", "trident", "fireworks", "fireball", "witherSkull", "thrown", "indirectMagic" -> {
                var projectile = damageSource.getSource();
                assert projectile != null;

                var attacker = damageSource.getAttacker();
                if (attacker == null) {
                    yield "Proyectil (" + projectile.getName().getString() + colorChars + ")";
                } else {
                    TheAbyss.getLogger().info(String.valueOf(attacker.getName()));
                    yield "Proyectil (" + projectile.getName().getString() + " -> " + attacker.getName().getString() + colorChars + ")";
                }
            }
            case "thorns" -> {
                var attacker = damageSource.getAttacker();
                assert attacker != null;

                yield "Espinas(" + attacker.getName().getString() + colorChars + ")";
            }
            case "explosion.player" -> {
                var attacker = damageSource.getAttacker();
                assert attacker != null;

                yield "Explosión (" + attacker.getName().getString() + colorChars + ")";
            }
            default -> "Desconocido.";
        };
    }

}
