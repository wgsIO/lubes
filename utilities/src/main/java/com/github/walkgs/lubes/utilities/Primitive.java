package com.github.walkgs.lubes.utilities;

import java.util.HashMap;
import java.util.Map;

public class Primitive {
    private static final Map<Class<?>, Class<?>> PRIMITIVE_MAP = new HashMap<Class<?>, Class<?>>() {{
        put(boolean.class, Boolean.class);
        put(byte.class, Byte.class);
        put(char.class, Character.class);
        put(double.class, Double.class);
        put(float.class, Float.class);
        put(int.class, Integer.class);
        put(long.class, Long.class);
        put(short.class, Short.class);
    }};

    public static Class<?> translate(Class<?> type) {
        final Class<?> conversion = PRIMITIVE_MAP.get(type);
        return conversion != null ? conversion : type;
    }

}
