package club.theabyss.server.global.mixins.server.entites.peaceful;

import club.theabyss.TheAbyssManager;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.AxolotlEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//TODO AÑADIR TASK DE ATAQUE CON EL BRAIN EN VEZ DE CON PATHFINDING.
@Mixin(AxolotlEntity.class)
public class AxolotlEntityMixin extends MobEntity {

    protected AxolotlEntityMixin(EntityType<? extends MobEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("RETURN"), method = "createAxolotlAttributes")
    private static void addAttackDamageAttribute(CallbackInfoReturnable<DefaultAttributeContainer.Builder> cir) {
        cir.getReturnValue().add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public void initGoals() {
        var day = (TheAbyssManager.getInstance().serverCore().serverGameManager() != null) ? TheAbyssManager.getInstance().serverCore().serverGameManager().day() : 0;
        if (day >= 7) {
            targetSelector.add(0, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
            goalSelector.add(0, new MeleeAttackGoal(((AxolotlEntity) (Object) this), 1.0D, false));
        }
    }

}
