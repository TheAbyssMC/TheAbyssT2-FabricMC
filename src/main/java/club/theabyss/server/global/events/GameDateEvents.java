package club.theabyss.server.global.events;

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

public class GameDateEvents {

    @FunctionalInterface
    public interface DayHasElapsedEvent {
        Event<GameDateEvents.DayHasElapsedEvent> EVENT = EventFactory.createArrayBacked(GameDateEvents.DayHasElapsedEvent.class,
                (listeners) -> (day) -> {
                    for (GameDateEvents.DayHasElapsedEvent listener : listeners) {
                        ActionResult actionResult = listener.changeDay(day);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult changeDay(long day);
    }

}
