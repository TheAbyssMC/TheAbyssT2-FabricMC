package club.theabyss.server.game.entity.entities.watercreatures;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.SquidEntity;
import net.minecraft.world.World;

public class KrakenEntity extends SquidEntity {
    public KrakenEntity(EntityType<? extends SquidEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {



            return true;
        }
        return false;
    }



}
