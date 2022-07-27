package club.theabyss.server.global.utils.customGlyphs;

public class NoSuchAnimationException extends Exception {
    public NoSuchAnimationException(String animation) {
        super("The animation \"" + animation + "\" was never loaded");
    }
}
