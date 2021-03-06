package dontstarvecookbook.core.utils;

public class StringUtilities {

    private StringUtilities() {}

    public static String removeTrailingChars(String s, char toRemove) {
        StringBuilder sb = new StringBuilder(s);
        sb = sb.reverse();
        while (sb.charAt(0) == toRemove) {
            sb.replace(0, 1, "");
            if (sb.length() <= 0) break;
        }
        return sb.reverse().toString();
    }

    public static String capitalize(String s) {
        if (s == null || s.isEmpty()) return "";
        if (s.length() == 1) return Character.toString(Character.toUpperCase(s.charAt(0)));
        return Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }
}
