package club.theabyss.server.game.entity.entities.creepers;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class ChargedCreeperEntity extends CreeperEntity {

    private static final TrackedData<Boolean> CHARGED = DataTracker.registerData(ChargedCreeperEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public ChargedCreeperEntity(EntityType<? extends CreeperEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.dataTracker.set(CHARGED, true);
        nbt.putBoolean("powered", true);
        super.readCustomDataFromNbt(nbt);
    }

}
