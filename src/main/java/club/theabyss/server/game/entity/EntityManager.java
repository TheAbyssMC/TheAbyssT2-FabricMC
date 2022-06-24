package club.theabyss.server.game.entity;

import club.theabyss.server.game.ServerGameManager;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.PrioritizedGoal;
import net.minecraft.entity.mob.MobEntity;

import java.lang.reflect.InvocationTargetException;

public class EntityManager {

    private static ServerGameManager serverGameManager;

    public EntityManager(final ServerGameManager serverGameManager) {
        EntityManager.serverGameManager = serverGameManager;
    }

    public static boolean[] reloadEntityPathfinders() {
        var worlds = serverGameManager.minecraftServer().getWorlds();

        boolean[] hasFailed = {false};

        worlds.forEach(world -> world.iterateEntities().forEach(entity -> {
            if (entity instanceof MobEntity mobEntity) {
                try {
                    var goalSelector = mobEntity.getClass().getField("goalSelector");
                    goalSelector.setAccessible(true);

                    var targetSelector = mobEntity.getClass().getField("targetSelector");
                    targetSelector.setAccessible(true);

                    var goalSelectorInstance = ((GoalSelector)goalSelector.get(mobEntity));
                    var targetSelectorInstance = ((GoalSelector)targetSelector.get(mobEntity));

                    goalSelectorInstance.getRunningGoals().forEach(PrioritizedGoal::stop);
                    targetSelectorInstance.getRunningGoals().forEach(PrioritizedGoal::stop);

                    goalSelectorInstance.clear();
                    targetSelectorInstance.clear();

                    var initGoals = entity.getClass().getMethod("initGoals");
                    initGoals.setAccessible(true);

                    initGoals.invoke(entity);
                } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | NoSuchFieldException ex) {
                    hasFailed[0] = true;
                    ex.printStackTrace();
                }
            }
        }));

        return hasFailed;
    }

}
