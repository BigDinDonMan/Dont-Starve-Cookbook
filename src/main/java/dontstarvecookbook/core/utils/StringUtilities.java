package dontstarvecookbook.core.utils;

public class StringUtilities {

    private StringUtilities() {}

    public static String removeTrailingChars(String s, char toRemove) {
        var sb = new StringBuilder(s);
        sb = sb.reverse();
        while (sb.charAt(0) == toRemove) {
            sb.replace(0, 1, "");
            if (sb.length() <= 0) break;
        }
        return sb.reverse().toString();
    }

}
