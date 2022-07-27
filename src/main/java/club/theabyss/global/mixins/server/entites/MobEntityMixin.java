package club.theabyss.global.mixins.server.entites;


import club.theabyss.global.interfaces.entity.IDataTracker;
import club.theabyss.global.interfaces.entity.IMobEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity implements IMobEntity {

    @Shadow @Final protected GoalSelector goalSelector;

    @Shadow @Final protected GoalSelector targetSelector;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Shadow protected abstract void initGoals();

    @Shadow protected abstract void initDataTracker();

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
