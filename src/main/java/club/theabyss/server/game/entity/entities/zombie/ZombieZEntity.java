package club.theabyss.server.game.entity.entities.zombie;

import club.theabyss.global.utils.GlobalDataAccess;
import club.theabyss.server.global.utils.chat.ChatFormatter;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.world.World;

public class ZombieZEntity extends ZombieEntity {

    public ZombieZEntity(EntityType<? extends ZombieEntity> entityType, World world) {
        super(entityType, world);
        setCustomName(ChatFormatter.stringFormatToText("&5Zombie Z"));

        if (GlobalDataAccess.getNowDay() < 14) {
            setHealth(30);
        } else {
            setHealth(40);
        }
    }

    public static DefaultAttributeContainer.Builder createZombieZAttributes() {
        var attributes = HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23000000417232513).add(EntityAttributes.GENERIC_ARMOR, 2.0).add(EntityAttributes.ZOMBIE_SPAWN_REINFORCEMENTS);

        if (GlobalDataAccess.getNowDay() < 14) {
            attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 12);
            attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 30);
        } else {
            attributes.add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 16);
            attributes.add(EntityAttributes.GENERIC_MAX_HEALTH, 40);
        }

        return attributes;
    }

}
