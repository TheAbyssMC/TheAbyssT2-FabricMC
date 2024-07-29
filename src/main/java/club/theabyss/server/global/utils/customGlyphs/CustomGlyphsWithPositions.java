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

@SuppressWarnings({"unused", ""})
public class CustomGlyphsWithPositions extends CustomGlyphs {
    private static final HashMap<Character, Integer> widths = new HashMap<>(0, 1);


    private static void declare(char glyph, int width) throws NoGlyphException { // Declare width for glyph
        for (char glyph_ : aliases.keySet()) {
            if(glyph == glyph_) {
                widths.put(glyph, width);
                return;
            }
        }
        throw new NoGlyphException(glyph);
    }
    private static void declare(String alias, int width) throws NoAliasException { // Declare width for glyph (referenced by alias)
        for (char glyph : aliases.keySet()) {
            for (String alias_ : aliases.get(glyph)) {
                if (alias.equals(alias_)) {
                    widths.put(glyph, width);
                    return;
                }
            }
        }
        throw new NoAliasException(alias);
    }

    public static void declare(String alias, char glyph, int width) throws Exception { // Declare alias and width for glyph
        declare(alias, glyph, width, false);
    }
    public static void declare(String alias, char glyph, int width, boolean suppressConflictIfEquals) throws Exception { // Declare alias and width for glyph
        declare(alias, glyph, suppressConflictIfEquals);
        declare(glyph, width);
    }
    public static void declare(String[] aliases, char glyph, int width) throws Exception { // Declare aliases and width for glyph
        declare(aliases, glyph, width, false);
    }
    public static void declare(String[] aliases, char glyph, int width, boolean suppressConflictIfEquals) throws Exception { // Declare aliases and width for glyph
        for (String alias : aliases) declare(alias, glyph, width, suppressConflictIfEquals);
    }

    public static void declare(HashMap<Character, Object[]> jsonContent) throws Exception { // Automatically declare all glyphs with a JSON file
        declare(jsonContent, false);
    }
    public static void declare(HashMap<Character, Object[]> jsonContent, boolean suppressConflictIfEquals) throws Exception { // Automatically declare all glyphs with a JSON file
        for (char glyph : jsonContent.keySet()) {
            Object[] obj = jsonContent.get(glyph);
            declare((String[]) obj[0], glyph, (int) obj[1], suppressConflictIfEquals);
        }
    }


    public enum MinecraftWouldAutomaticallyAlignTheDesiredTypeOfTextToThe {
        StartOfTheScreen,
        CenterOfTheScreen
    }

    private final MinecraftWouldAutomaticallyAlignTheDesiredTypeOfTextToThe absolutePositioningOption;

    public CustomGlyphsWithPositions(MinecraftWouldAutomaticallyAlignTheDesiredTypeOfTextToThe absolutePositioningOption) {
        this.absolutePositioningOption = absolutePositioningOption;
    }

    private final ArrayList<Object> textAndMargins = new ArrayList<>();

    @Override
    public String toString() {
        // Validate the textAndMargins object array
        if (textAndMargins.get(0) instanceof String) textAndMargins.add(0, 0);
        int lastIndex = textAndMargins.size()-1;
        if (lastIndex < 1) return "";
        if (textAndMargins.get(lastIndex) instanceof Float) {
            // TODO Validation should not modify the array
            textAndMargins.remove(lastIndex);
        }

        // Transform the margins so that they are absolute (or relative to the center of the screen)
        @SuppressWarnings("unchecked")
        ArrayList<Object> textAndMargins = (ArrayList<Object>) this.textAndMargins.clone();
        if (absolutePositioningOption == MinecraftWouldAutomaticallyAlignTheDesiredTypeOfTextToThe.CenterOfTheScreen) {
            AbsolutePositionToAmberWatMargins.textInTheCenterOfTheScreen(textAndMargins, widths);
        } else {
            AbsolutePositionToAmberWatMargins.textInTheStartOfTheScreen(textAndMargins, widths);
        }

        // Convert the margins into the corresponding glyphs
        StringBuilder str = new StringBuilder();
        for (Object obj : textAndMargins) str.append(obj instanceof Float ? AmberWatMargins.marginToChars((float) obj) : obj);
        return str.toString();
    }

    public void set(String str) {
        str = get(str);
        int previousObjectIndex = textAndMargins.size()-1;
        Object previousObject = textAndMargins.get(previousObjectIndex);
        if (previousObjectIndex >= 0 && previousObject instanceof String) {
            textAndMargins.set(previousObjectIndex, previousObject + str);
        } else {
            textAndMargins.add(str);
        }
    }

    public void set(float x) {
        int previousObjectIndex = textAndMargins.size()-1;
        if (previousObjectIndex >= 0 && textAndMargins.get(previousObjectIndex) instanceof Float) {
            System.out.println("[ WARNING ] A margin of " + x + " units has been set so that the text of the next call to this method is positioned accordingly. However, the previous call to this method was also made to set a margin to what would have been a piece of text. Ignoring the first call of this method...");
            textAndMargins.remove(previousObjectIndex);
        }
        textAndMargins.add(x);
    }
}
