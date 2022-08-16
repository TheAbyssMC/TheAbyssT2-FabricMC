package club.theabyss.global.mixins.server.entites.peaceful;

import club.theabyss.global.interfaces.server.entity.fox.IFoxEntity;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(FoxEntity.class)
public abstract class FoxEntityMixin implements IFoxEntity {

    @Shadow abstract void setWalking(boolean walking);

    @Override
    public void setWalking$0(boolean walking) {
        setWalking(walking);
    }

}
