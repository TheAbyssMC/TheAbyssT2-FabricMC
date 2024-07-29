package club.theabyss.server.data.storage;

import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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

public class DeathMessages {

    private final Map<UUID, String> deathMessages;

    public DeathMessages() {
        this.deathMessages = new HashMap<>();
    }

    public DeathMessages(HashMap<UUID, String> deathMessages) {
        this.deathMessages = deathMessages;
    }

    /**
     * @return the death messages.
     */
    public Map<UUID, String> deathMessages() {
        return deathMessages;
    }

    public String deathMessage(ServerPlayerEntity player) {
        return deathMessages.containsKey(player.getUuid()) ? deathMessages.get(player.getUuid()) : defaultMessage();
    }

    /**
     * @return the default death message.
     */
    public String defaultMessage() {
        return "Nessuno puo sfuggire al destino scelto...";
    }

}
