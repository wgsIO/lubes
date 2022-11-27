package com.github.walkgs.lubes.utilities;

public final class Strings {
    public static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public static boolean isBlank(String string) {
        boolean result = true;
        for (int i = 0; i < string.length(); i++)
            result = result && Character.isWhitespace(string.charAt(i));
        return result;
    }

    public static boolean isNullOrBlank(String string) {
        return isNullOrEmpty(string) || isBlank(string);
    }

}
