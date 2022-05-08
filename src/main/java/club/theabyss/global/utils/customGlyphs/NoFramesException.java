package club.theabyss.global.utils.customGlyphs;

public class NoFramesException extends Exception {
    NoFramesException(String animation) {
        super("An attempt was made to load the animation \"" + animation + "\". However, this action failed because such animation does not have any frames.");
    }
}
