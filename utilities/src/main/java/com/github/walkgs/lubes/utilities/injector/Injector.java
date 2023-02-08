package com.github.walkgs.lubes.utilities.injector;

import java.io.Closeable;
import java.lang.reflect.InvocationTargetException;


public interface Injector extends Closeable {

    <T> T inject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;

    <T> T injectConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;
    <T> T injectMethods(T instance, Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException;
    <T> T injectFields(T instance, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;
    <T> T getSingleton(String name, Class<?> type) throws InvocationTargetException, InstantiationException, IllegalAccessException;

}
