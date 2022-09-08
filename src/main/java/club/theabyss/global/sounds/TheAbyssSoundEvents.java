package club.theabyss.global.sounds;

import club.theabyss.global.utils.TheAbyssConstants;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class TheAbyssSoundEvents {

    public static SoundEvent WEAVER_SPIDER_WEAVING;
    public static SoundEvent FLASHBANG_STATIC;

    public static void register() {
        FLASHBANG_STATIC = register("ambient.flashbang.static");
        WEAVER_SPIDER_WEAVING = register("entity.weaver_spider.weaving");
    }

    private static SoundEvent register(String id) {
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(new Identifier(TheAbyssConstants.MOD_ID, id)));
    }

}
