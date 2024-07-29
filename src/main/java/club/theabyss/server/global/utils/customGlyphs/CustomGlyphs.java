package club.theabyss.server.global.utils.customGlyphs;

import java.util.ArrayList;
import java.util.HashMap;

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

public class CustomGlyphs {
    protected static final HashMap<Character, ArrayList<String>> aliases = new HashMap<>(0, 1);

    public static void declare(String alias, char glyph, boolean suppressConflictIfEquals) throws AliasAlreadyExistsException { // Declare alias for glyph
        for (ArrayList<String> values : aliases.values()) {
            for (String alias_ : values) {
                if (alias_.contains(alias) || alias.contains(alias_)) {
                    if (suppressConflictIfEquals && alias.equals(alias_)) {
                        return;
                    }
                    throw new AliasAlreadyExistsException(alias, glyph, alias_);
                }
            }
        }
        aliases.computeIfAbsent(glyph, k -> new ArrayList<>());
        aliases.get(glyph).add(alias);
    }

    public static String get(String str) {
        for (char glyph : aliases.keySet()) {
            for (String alias : aliases.get(glyph))
                str = str.replace(alias, Character.toString(glyph));
        }
        return str;
    }
}
