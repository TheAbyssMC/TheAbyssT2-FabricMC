package club.theabyss.global.mixins.server.entites;


import club.theabyss.global.interfaces.entity.IDataTracker;
import club.theabyss.global.interfaces.entity.IMobEntity;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements IMobEntity {

    @Shadow @Final protected GoalSelector goalSelector;

    @Shadow @Final protected GoalSelector targetSelector;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow protected abstract void initGoals();

    @Shadow protected abstract void initDataTracker();

    @Shadow protected abstract float getDropChance(EquipmentSlot slot);

    /* FIXME
    Cancels every "illegal" drop (items with non-vanilla enchantment levels) for the equipment except for the player entities. This can cause conflicts with other Mods,
    Plugins or DataPacks if they have entities with drops that contains non-vanilla enchantment levels.
     */
    @Redirect(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;getDropChance(Lnet/minecraft/entity/EquipmentSlot;)F"))
    private float modifyDropChance(MobEntity instance, EquipmentSlot slot) {
        if (shouldDropEquipment(instance, slot) || instance.getType().equals(EntityType.PLAYER)) {
            return getDropChance(slot);
        } else {
            return 0;
        }
    }

    public boolean shouldDropEquipment(MobEntity mobEntity, EquipmentSlot equipmentSlot) {
        var itemStack = mobEntity.getEquippedStack(equipmentSlot);

        return isValidItem(itemStack);
    }

    public boolean isValidItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) return false;

        var enchantments = EnchantmentHelper.get(itemStack);

        return hasValidEnchantments(enchantments);
    }

    public boolean hasValidEnchantments(Map<Enchantment, Integer> enchantments) {
        AtomicBoolean isValid = new AtomicBoolean(true);
        enchantments.forEach((enchantment, integer) -> {
            if (integer > enchantment.getMaxLevel()) isValid.set(false);
        });
        return isValid.get();
    }

    public void reloadGoals() {
        goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
        targetSelector.getRunningGoals().forEach(PrioritizedGoal::stop);

        goalSelector.clear();
        targetSelector.clear();

        initGoals();
    }

    public void reloadDataTracker() {
        var IDataTracker = ((IDataTracker)dataTracker);

        IDataTracker.reloadTrackedDataEntries();
    }

}
