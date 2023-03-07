package com.github.walkgs.lubes.utilities.callable;

import com.github.walkgs.lubes.utilities.injector.Injector;
import com.github.walkgs.lubes.utilities.injector.disposed.SimplePriorityComparator;
import com.github.walkgs.lubes.utilities.seekers.Methods;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class SimpleCallableManager implements CallableManager {

    private static final SimplePriorityComparator PRIORITY_COMPARATOR = new SimplePriorityComparator();

    private final Injector injector;

    @Override
    public void invoke(Object instance, Method method, Object[] args) throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @Override
    public void invoke(Object instance, String code, Object[] args) throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    @Override
    public void invoke(Object instance, Method method) {
        try {
            injector.injectAtMethod(instance, method);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void invoke(Object instance, String code) {
        final Collection<Method> methods = findMethods(instance, code);
        for (Method method : methods) {
            invoke(instance, method);
        }
    }


    private Collection<Method> findMethods(Object instance, String code) {
        final Class<?> clazz = instance.getClass();
        return Methods.find(clazz).filter(it -> {
            final Callable annotation = it.getAnnotation(Callable.class);
            if (annotation == null || !annotation.value().equalsIgnoreCase(code))
                return false;
            return true;
        }).sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
    }


}
