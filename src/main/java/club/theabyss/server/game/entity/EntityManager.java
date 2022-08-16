package club.theabyss.server.game.entity;

import club.theabyss.global.interfaces.server.entity.IMobEntity;
import club.theabyss.global.interfaces.server.entity.skeleton.IAbstractSkeletonEntity;
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
                if (entity instanceof AbstractSkeletonEntity skeletonEntity) {
                    skeletonEntity.updateAttackType();
                    ((IAbstractSkeletonEntity)skeletonEntity).initEquipment$0(skeletonEntity.getWorld().getLocalDifficulty(skeletonEntity.getBlockPos()));
                }
                mob.reloadDataTracker();
            }
        }));
    }

}
