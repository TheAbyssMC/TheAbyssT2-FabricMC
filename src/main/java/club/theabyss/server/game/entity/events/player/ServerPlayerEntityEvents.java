package club.theabyss.server.game.entity.events.player;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
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

public class ServerPlayerEntityEvents {

    @FunctionalInterface
    public interface PlayerDeath {
        Event<PlayerDeath> EVENT = EventFactory.createArrayBacked(PlayerDeath.class,
                (listeners) -> (player, source) -> {
                    for (PlayerDeath listener : listeners) {
                        ActionResult actionResult = listener.die(player, source);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult die(ServerPlayerEntity entity, DamageSource source);
    }

    @FunctionalInterface
    public interface PlayerResurrect {
        Event<PlayerResurrect> EVENT = EventFactory.createArrayBacked(PlayerResurrect.class,
                (listeners) -> (playerEntity) -> {
                    for (PlayerResurrect listener : listeners) {
                        ActionResult actionResult = listener.resurrect(playerEntity);

                        if (actionResult != ActionResult.PASS) {
                            return actionResult;
                        }
                    }
                    return ActionResult.PASS;
                });
        ActionResult resurrect(ServerPlayerEntity playerEntity);
    }

}
