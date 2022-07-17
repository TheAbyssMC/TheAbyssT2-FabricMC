package club.theabyss.global.mixins.server.entites;


import club.theabyss.global.interfaces.entity.IMobEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin implements IMobEntity {

    @Shadow @Final protected GoalSelector goalSelector;

    @Shadow @Final protected GoalSelector targetSelector;

    @Shadow protected abstract void initGoals();

    public void reloadGoals() {
        goalSelector.getRunningGoals().forEach(PrioritizedGoal::stop);
        targetSelector.getRunningGoals().forEach(PrioritizedGoal::stop);

        goalSelector.clear();
        targetSelector.clear();

        initGoals();
    }

}
