package com.github.walkgs.lubes.utilities.injector;

import java.util.Map;

public interface Storage {

    Map<Class<?>, ElementList<Class<?>>> getInjectables();

    Map<Class<?>, ElementList<Object>> getSingletons();


}
