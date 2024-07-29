package club.theabyss.server.game.totem.data;

import lombok.Getter;

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

public class TotemData {

    private final @Getter Map<UUID, Long> usedTotemsMap;

    public TotemData() {
        this.usedTotemsMap = new HashMap<>();
    }

    public TotemData(Map<UUID, Long> usedTotemsMap) {
        this.usedTotemsMap = usedTotemsMap;
    }

}
