package club.theabyss.server.global.sounds;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class AbyssSoundEvents {

    public static final SoundEvent WEAVER_SPIDER_WEAVING = register("entity.weaver.spider.weaving");

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(id)));
    }

}
