package com.github.walkgs.lubes.utilities.seekers;

public class Enums {
    public static Enum<?> find(Class<?> clazz, String name) {
        final Enum<?>[] constants = (Enum<?>[]) clazz.getEnumConstants();
        for (Enum<?> constant : constants) {
            if (!constant.name().equalsIgnoreCase(name))
                continue;
            return constant;
        }
        return null;
    }

}
