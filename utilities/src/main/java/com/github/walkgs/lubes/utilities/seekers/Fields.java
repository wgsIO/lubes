package com.github.walkgs.lubes.utilities.seekers;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Stream;

public class Fields {

    public static Stream<Field> find(Class<?> clazz) {
        return Arrays.stream(clazz.getFields());
    }

    public static Stream<Field> findDeclared(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields());
    }

}
