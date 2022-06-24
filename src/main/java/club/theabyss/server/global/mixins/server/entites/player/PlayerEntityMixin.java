package club.theabyss.server.global.mixins.server.entites.player;

import club.theabyss.TheAbyssManager;
import club.theabyss.server.game.skilltree.enums.Skills;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @ModifyVariable(method = "applyDamage", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public float modifyAmount(float amount) {
        var serverManager = TheAbyssManager.getInstance().serverCore();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Resistance);

            if (level >= 1) {
                return (float) (amount * (1 - (level * 0.025)));
            } else {
                return amount;
            }
        } else {
            return amount;
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    public float modifyAttackDamageForE(float amount) {
        var serverManager = TheAbyssManager.getInstance().serverCore();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength);

            if (level >= 1) {
                return (float) (amount * (1+(skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength) * 0.025)));
            } else {
                return amount;
            }
        } else {
            return amount;
        }
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), index = 1)
    public float modifyAttackDamageForLE(float amount) {
        var serverManager = TheAbyssManager.getInstance().serverCore();
        var skillTreeManager = serverManager.skillTreeManager();

        if (((PlayerEntity)(Object)this) instanceof ServerPlayerEntity serverPlayerEntity) {

            var level = skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength);

            if (level >= 1) {
                var damage = (float) (amount * (1+(skillTreeManager.getSkillLevel(serverPlayerEntity, Skills.Strength) * 0.025)));
                return 1.0F + (EnchantmentHelper.getSweepingMultiplier(((PlayerEntity)(Object) this)) * damage);
            } else {
                return amount;
            }
        } else {
            return amount;
        }
    }

}
