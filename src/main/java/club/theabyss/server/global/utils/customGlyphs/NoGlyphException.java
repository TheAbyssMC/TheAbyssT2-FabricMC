package club.theabyss.server.global.utils.customGlyphs;

public class NoGlyphException extends Exception {
    NoGlyphException(char ch) {
        super("'\\u" + Integer.toHexString(ch) + "' is not a declared glyph ");
    }
}
