package club.theabyss.server.global.utils.timedTitle;

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

public class InvalidTitleTimings extends Exception {
    InvalidTitleTimings(TimedTitle.Title title) {
        super("The timings of the title \"" + title.title + "\" (with a subtitle of \"" + title.subtitle + "\") are invalid. Timings:\nFade in: " + title.fadeIn + "\nStay time: " + title.stayTime + "\nMinimum stay time: " + title.minimumStayTime + "\nFade out: " + title.fadeOut);
    }

    InvalidTitleTimings(TimedActionBar.ActionBar actionBar) {
        super("The timings of the action bar \"" + actionBar.text + "\" are invalid. Timings:\nFade in: 0 (Minecraft default, can't be changed)\nStay time: 50 (Minecraft default, can't be changed)\nMinimum stay time: " + actionBar.stayTime + "\nFade out: 10 (Minecraft default, can't be changed)");
    }
}
