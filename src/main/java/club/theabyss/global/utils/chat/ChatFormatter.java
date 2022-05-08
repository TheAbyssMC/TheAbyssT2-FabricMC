package club.theabyss.global.utils.chat;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.Validate;

public class ChatFormatter {

    public static final String name = ChatFormatter.format("&c&lThe&6&lAbyss&r");
    public static final String prefix = ChatFormatter.format(name + " &7>> ");

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static Text textFormat(String text) {return Text.of(translateAlternateColorCodes('&', text));}

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static Text textFormatWithPrefix(String text) {return Text.of(prefix + format(text));}

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static String format(String text) {return translateAlternateColorCodes('&', text);}

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static String formatWithPrefix(String text) {return prefix + format(text);}

    /**
     * Translates a string using an alternate color code character into a
     * string that uses the internal Formatting.FORMATTING_CODE_PREFIX color code
     * character. The alternate color code character will only be replaced if
     * it is immediately followed by 0-9, A-F, a-f, K-O, k-o, R or r.
     *
     * @param altColorChar The alternate color code character to replace. Ex: {@literal &}
     * @param text Text containing the alternate color code character.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character.
     */
    public static String translateAlternateColorCodes(char altColorChar, String text) {
        Validate.notNull(text, "Cannot translate null text.");

        char[] b = text.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(b[i + 1]) > -1) {
                b[i] = Formatting.FORMATTING_CODE_PREFIX;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

}
