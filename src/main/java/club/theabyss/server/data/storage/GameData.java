package club.theabyss.server.data.storage;

import club.theabyss.server.game.bloodmoon.types.BloodMoonData;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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

public class GameData {

    private @Setter LocalDate startDate;

    private final BloodMoonData bloodMoonData;

    public GameData(BloodMoonData bloodMoonData, LocalDate startDate) {
        this.bloodMoonData = bloodMoonData;
        this.startDate = startDate;
    }

    public GameData(BloodMoonData bloodMoonData) {
        this(bloodMoonData, LocalDate.now());
    }

    /**
     * @return the day of the Game.
     */
    public long day() {
        return ChronoUnit.DAYS.between(startDate, LocalDate.now());
    }

    /**
     * @return the BloodMoonData instance.
     */
    public BloodMoonData bloodMoonData() {
        return this.bloodMoonData;
    }

}
