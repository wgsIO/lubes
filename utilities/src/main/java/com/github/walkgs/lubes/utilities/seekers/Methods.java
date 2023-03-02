package com.github.walkgs.lubes.utilities.seekers;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

public class Methods {

    public static Stream<Method> find(Class<?> clazz) {
        return Arrays.stream(clazz.getMethods());
    }

    public static Stream<Method> findDeclared(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredMethods());
    }

}
