package club.theabyss.server.game.entity.entities.raids;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.VexEntity;
import net.minecraft.world.World;

public class ElementalSpectrumEntity extends VexEntity implements Monster {

    public ElementalSpectrumEntity(EntityType<? extends VexEntity> entityType, World world) {
        super(entityType, world);
        setHealth(14 * 1.25F);
    }

    public static DefaultAttributeContainer.Builder createElementalSpectrumAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14 * 1.25F).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 13.5 * 1.15F);
    }

}
