package club.theabyss.server.game.entity;

import club.theabyss.global.interfaces.entity.IMobEntity;
import club.theabyss.server.game.ServerGameManager;
import net.minecraft.entity.mob.MobEntity;

public class EntityManager {

    private static ServerGameManager serverGameManager;

    public EntityManager(final ServerGameManager serverGameManager) {
        EntityManager.serverGameManager = serverGameManager;
    }

    public static void reloadGoals() {
        var worlds = serverGameManager.minecraftServer().getWorlds();

        worlds.forEach(world -> world.iterateEntities().forEach(entity -> {
            if (entity instanceof MobEntity mobEntity) {
                ((IMobEntity)mobEntity).reloadGoals();
            }
        }));
    }

}
