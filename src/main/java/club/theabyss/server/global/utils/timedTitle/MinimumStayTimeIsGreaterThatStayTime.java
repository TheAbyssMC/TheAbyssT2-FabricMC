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

public class MinimumStayTimeIsGreaterThatStayTime extends Exception {
    MinimumStayTimeIsGreaterThatStayTime(TimedTitle.Title title) {
        super("The title \"" + title.title + "\" (with a subtitle of \"" + title.subtitle + "\", a fadeIn of " + title.fadeIn + " and a fadeOut of " + title.fadeOut + ") has been initialized with a stayTime of " + title.stayTime + " ticks and a minimumStayTime of " + title.minimumStayTime);
    }

    MinimumStayTimeIsGreaterThatStayTime(TimedActionBar.ActionBar actionBar) {
        super("The action bar \"" + actionBar.text + "\" has been initialized with a stayTime of " + actionBar.stayTime + " ticks and a minimumStayTime of " + actionBar.minimumStayTime);
    }
}
