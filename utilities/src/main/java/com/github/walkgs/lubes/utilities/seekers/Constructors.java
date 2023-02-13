package com.github.walkgs.lubes.utilities.seekers;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.stream.Stream;

public class Constructors {

    public static Stream<Constructor<?>> find(Class<?> clazz) {
        return Arrays.stream(clazz.getConstructors());
    }

    public static Stream<Constructor<?>> findDeclared(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredConstructors());
    }

}
