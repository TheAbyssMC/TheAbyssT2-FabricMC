package club.theabyss.server.game.bloodmoon;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.util.ActionResult;

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

public class BloodMoonEvents {

    public interface BloodMoonStarted {
        Event<BloodMoonStarted> EVENT = EventFactory.createArrayBacked(BloodMoonStarted.class,
                (listeners) -> (bloodMoon) -> {
            for (BloodMoonStarted listener : listeners) {
                ActionResult result = listener.start(bloodMoon);

                if (result != ActionResult.PASS) {
                    return result;
                }
            }
            return ActionResult.PASS;
        });

        ActionResult start(BloodMoonManager bloodMoonManager);
    }

    public interface BloodMoonEnded {
        Event<BloodMoonEnded> EVENT = EventFactory.createArrayBacked(BloodMoonEnded.class,
                (listeners) -> (bloodMoon) -> {
                    for (BloodMoonEnded listener : listeners) {
                        ActionResult result = listener.end(bloodMoon);

                        if (result != ActionResult.PASS) {
                            return result;
                        }
                    }
                    return ActionResult.PASS;
                });

        ActionResult end(BloodMoonManager bloodMoonManager);
    }

}
