package club.theabyss.global.mixins.server.entites.projectile;

import club.theabyss.global.interfaces.server.item.ITridentEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TridentEntity.class)
public class TridentEntityMixin implements ITridentEntity {

    @Shadow private ItemStack tridentStack;

    @Override
    public ItemStack getTridentStack$0() {
        return tridentStack;
    }

    @Override
    public void setTridentStack$0(ItemStack itemStack) {
        tridentStack = itemStack;
    }

}
