package club.theabyss.server.game.entity;

import club.theabyss.TheAbyssManager;
import club.theabyss.global.interfaces.entity.IMobEntity;
import club.theabyss.server.game.ServerGameManager;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import net.minecraft.entity.mob.MobEntity;

public class EntityManager {

    private static ServerGameManager serverGameManager;

    public EntityManager(final ServerGameManager serverGameManager) {
        EntityManager.serverGameManager = serverGameManager;
    }

    public static void reloadEntities() {
        var worlds = serverGameManager.minecraftServer().getWorlds();

        worlds.forEach(world -> world.iterateEntities().forEach(entity -> {
            if (entity instanceof MobEntity mobEntity) {
                var mob = ((IMobEntity)mobEntity);
                mob.reloadGoals();
                if (entity instanceof AbstractSkeletonEntity skeletonEntity) skeletonEntity.updateAttackType();
                mob.reloadDataTracker();
            }
        }));
    }

}
