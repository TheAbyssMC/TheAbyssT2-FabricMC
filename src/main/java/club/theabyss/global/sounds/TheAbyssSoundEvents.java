package club.theabyss.global.sounds;

import club.theabyss.global.utils.TheAbyssConstants;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

/*
 *
 * Copyright (C) Vermillion Productions. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
