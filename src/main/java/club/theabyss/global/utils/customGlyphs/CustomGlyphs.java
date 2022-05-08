package club.theabyss.global.utils.customGlyphs;

import java.util.ArrayList;
import java.util.HashMap;

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
