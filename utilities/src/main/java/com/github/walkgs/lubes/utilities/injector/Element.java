package com.github.walkgs.lubes.utilities.injector;

public interface Element<T> {

    static String DEFAULT_NAME = "DEFAULT NAME";

    String getName();

    T get();


}
