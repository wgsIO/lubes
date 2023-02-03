package com.github.walkgs.lubes.utilities.injector;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;


public interface Injector extends Closeable {

    <T> T inject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;

}
