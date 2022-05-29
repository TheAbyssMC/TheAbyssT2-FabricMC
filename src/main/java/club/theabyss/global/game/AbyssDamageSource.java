package club.theabyss.global.game;

import net.minecraft.entity.damage.DamageSource;

public class AbyssDamageSource extends DamageSource {

    public static DamageSource SKILLTREE = new AbyssDamageSource("skilltree");

    public AbyssDamageSource(String name) {
        super(name);
    }

}
