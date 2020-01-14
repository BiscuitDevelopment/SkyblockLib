package codes.biscuit.skyblocklib.utils;

import java.util.regex.Pattern;

/**
 * Collection of text/string related utility methods
 */
public class TextUtils {

    private static final Pattern STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-OR]");
    private static final Pattern LETTERS_NUMBERS = Pattern.compile("[^a-z A-Z:0-9/']");
    private static final Pattern NUMBERS_SLASHES = Pattern.compile("[^0-9 /]");

    private TextUtils() {}

    /**
     * Strips color codes from a given text
     *
     * @param input Text to strip colors from
     * @return Text without color codes
     */
    public static String stripColor(final String input) {
        return STRIP_COLOR_PATTERN.matcher(input).replaceAll("");
    }

    /**
     * Removes any character that isn't a number or letter from a given text.
     *
     * @param text Input text
     * @return Input text with only letters and numbers
     */
    public static String keepLettersAndNumbersOnly(String text) {
        return LETTERS_NUMBERS.matcher(text).replaceAll("");
    }

    /**
     * Get the ordinal suffix like "st", "nd", "rd" and "th" of a number.
     *
     * @param n Number to get the suffix of
     * @return Ordinal suffix
     */
    public static String getOrdinalSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th";
        }
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }


    /**
     * Removes any character that isn't a number from a given text.
     *
     * @param text Input text
     * @return Input text with only numbers
     */
    public static String getNumbersOnly(String text) {
        return NUMBERS_SLASHES.matcher(text).replaceAll("");
    }

}
