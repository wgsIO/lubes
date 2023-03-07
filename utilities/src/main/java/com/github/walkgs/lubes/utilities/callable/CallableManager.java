package com.github.walkgs.lubes.utilities.callable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface CallableManager {

    void invoke(Object instance, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException;

    void invoke(Object instance, String code, Object[] args) throws InvocationTargetException, IllegalAccessException;

    void invoke(Object instance, Method method) throws InvocationTargetException, IllegalAccessException;

    void invoke(Object instance, String code) throws InvocationTargetException, IllegalAccessException;

}
