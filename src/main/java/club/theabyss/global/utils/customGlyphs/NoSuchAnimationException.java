package club.theabyss.global.utils.customGlyphs;

public class NoSuchAnimationException extends Exception {
    NoSuchAnimationException(String animation) {
        super("The animation \"" + animation + "\" was never loaded");
    }
}
