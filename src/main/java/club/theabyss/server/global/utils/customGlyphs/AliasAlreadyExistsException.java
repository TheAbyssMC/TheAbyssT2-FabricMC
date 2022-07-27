package club.theabyss.server.global.utils.customGlyphs;

class AliasAlreadyExistsException extends Exception {
    AliasAlreadyExistsException(String alias_1, char glyph_1, String alias_2) {
        super("The alias \"" + alias_1 + "\" could not be assigned to the glyph '\\u" + Integer.toHexString(glyph_1) + "' because it conflicts with the alias \"" + alias_2 + "\"");
    }
}
