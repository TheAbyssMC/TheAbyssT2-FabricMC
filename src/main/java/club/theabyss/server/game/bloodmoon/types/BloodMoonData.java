package club.theabyss.server.game.bloodmoon.types;

import lombok.Getter;
import lombok.Setter;

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

/**
 * Class designed to hold all the data related to the BloodMoon event.
 */
public class BloodMoonData {

    private @Getter @Setter long endsIn;
    private @Getter @Setter long totalTime;
    private @Getter @Setter long naturalBloodMoonIn;

    public BloodMoonData() {
        this.endsIn = 0;
        this.totalTime = 0;
        this.naturalBloodMoonIn = 0;
    }

    public BloodMoonData(long endsIn, long totalTime, long naturalBloodMoonIn) {
        this.endsIn = endsIn;
        this.totalTime = totalTime;
        this.naturalBloodMoonIn = naturalBloodMoonIn;
    }

}
