package club.theabyss.global.utils.customGlyphs;

public class NoAliasException extends Exception {
    NoAliasException(String alias) {
        super("\"" + alias + "\" is not an alias of any declared glyph");
    }
}
