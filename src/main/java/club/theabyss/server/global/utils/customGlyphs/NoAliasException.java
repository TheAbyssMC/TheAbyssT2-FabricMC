package club.theabyss.server.global.utils.customGlyphs;

public class NoAliasException extends Exception {
    NoAliasException(String alias) {
        super("\"" + alias + "\" is not an alias of any declared glyph");
    }
}
