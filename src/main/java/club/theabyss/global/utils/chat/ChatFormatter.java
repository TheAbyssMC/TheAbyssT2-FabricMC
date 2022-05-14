package club.theabyss.global.utils.chat;

import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.apache.commons.lang3.Validate;

public class ChatFormatter {

    public static final String name = ChatFormatter.stringFormatToString("&c&lThe&6&lAbyss&r");
    public static final String prefix = ChatFormatter.stringFormatToString(name + " &7>> ");

    /**
     * Function to translate the given String into Text Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static Text stringFormatToText(String text) {return Text.of(translateAlternateColorCodes('&', text));}

    /**
     * Function to translate the given String into Text Formatting format with the mod prefix.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&' with the mod prefix.
     */
    public static Text stringFormatWithPrefixToText(String text) {return Text.of(prefix + stringFormatToString(text));}

    /**
     * Function to translate the given text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static String stringFormatToString(String text) {return translateAlternateColorCodes('&', text);}

    /**
     * Function to translate the given String into Formatting format with the mod prefix.
     *
     * @param text to translate.
     * @return String containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&' with the mod prefix.
     */
    public static String stringFormatWithPrefixToString(String text) {return prefix + stringFormatToString(text);}

    /**
     * Function to translate the given Text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&' with the mod prefix.
     */
    public static String textFormatToString(Text text) {
        return text.getString();
    }

    /**
     * Function to translate the given Text into Formatting format with the mod prefix.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&' with the mod prefix.
     */
    public static String textFormatWithPrefixToString(Text text) {
        return prefix + textFormatToString(text);
    }

    /**
     * Function to translate the given Text into Formatting format.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&'.
     */
    public static Text textFormatToText(Text text) {
        return Text.of(translateAlternateColorCodes('&', text.getString()));
    }

    /**
     * Function to translate the given Text into Formatting format with the mod prefix.
     *
     * @param text to translate.
     * @return Text containing the Formatting.FORMATTING_CODE_PREFIX color code character replaced by '&' with the mod prefix.
     */
    public static Text textFormatWithPrefixToText(Text text) {
        return textFormatToText(Text.of(prefix + text));
    }

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
