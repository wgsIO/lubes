package com.github.walkgs.lubes.utilities.injector;

import java.io.Closeable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public interface Injector extends Closeable {

    <T> T inject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;

    <T> T injectViaConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;
    <T> T injectViaMethods(T instance, Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException;
    <T> T injectViaFields(T instance, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException;
    <T> T getSingleton(String name, Class<?> type) throws InvocationTargetException, InstantiationException, IllegalAccessException;

    <T> void injectAtMethod(T instance, Method method) throws InvocationTargetException, IllegalAccessException, InstantiationException;
    <T> void injectAtField(T instance, Field field) throws InstantiationException, IllegalAccessException, InvocationTargetException;

}
