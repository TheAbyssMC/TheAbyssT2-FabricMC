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

public class AbsolutePositionToAmberWatMargins {
    private static int getStringWidth(String str, HashMap<Character, Integer> widths) {
        return str.chars().mapToObj(o -> (char) o).toList().stream().reduce(0, (total, currentElem) -> total + (int) widths.get(currentElem), Integer::sum);
    }

    public static void textInTheCenterOfTheScreen(ArrayList<Object> result, HashMap<Character, Integer> widths) {
        int resultSize = result.size();
        for (int i = 0; i < resultSize; i += 2) result.set(i, 2 * (float) result.get(i));
        if (resultSize > 2) {
            for (int i = 2; i < resultSize; i += 2) {
                float pos1 = (float) result.get(0);
                float pos2 = (float) result.get(i);
                float w1 = 0;
                for (int j = 1; j < i; j++) {
                    if (result.get(j) instanceof Float) {
                        w1 += /* margins have their shadow already applied */ (float) result.get(j);
                    } else {
                        w1 += 1 /* shadow */ + getStringWidth((String) result.get(j), widths);
                    }
                }
                float w2 = 1 /* shadow */ + getStringWidth((String) result.get(i+1), widths);
                float m1 = (pos1 + pos2 + w2 - w1)/2;
                float m2 = pos2 - m1 - w1;
                result.set(0, m1);
                result.set(i, m2);
            }
        }
    }

    public static void textInTheStartOfTheScreen(ArrayList<Object> result, HashMap<Character, Integer> widths) {
        int resultSize = result.size();
        if (resultSize > 2) {
            for (int i = 2; i < resultSize; i += 2) {
                result.set(i, (float) result.get(i) - (getStringWidth((String) result.get(i-1), widths) + 1 /* shadow */) - (float) result.get(i-2));
            }
        }
    }
}
