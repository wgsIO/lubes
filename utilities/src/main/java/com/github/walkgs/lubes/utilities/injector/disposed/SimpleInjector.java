package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.injector.*;
import com.github.walkgs.lubes.utilities.injector.annotation.Inject;
import com.github.walkgs.lubes.utilities.injector.annotation.Name;
import com.github.walkgs.lubes.utilities.injector.annotation.Singleton;
import lombok.AllArgsConstructor;
import org.burningwave.core.classes.*;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class SimpleInjector implements Injector {

    private static final SimplePriorityComparator PRIORITY_COMPARATOR = new SimplePriorityComparator();

    private Syringe syringe;

    @Override
    public <T> T inject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        T instance = injectConstructor(clazz);

        injectFields(instance, clazz);

        //viaFields(instance, clazz)

        return injectMethods(instance, clazz);
    }

    public <T> T injectConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final List<Constructor<?>> constructors = Constructors.create().findAllAndMakeThemAccessible(ConstructorCriteria.forEntireClassHierarchy().allThoseThatMatch(it -> it.isAnnotationPresent(Inject.class)), clazz).stream().sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
        if (constructors.size() == 0)
            return (T) clazz.newInstance();
        else {
            final Constructor<?> constructor = constructors.get(0);
            return (T) constructor.newInstance(getParameterInstances(constructor.getParameterTypes(), constructor.getParameterAnnotations()));
        }
    }

    public <T> T injectMethods(T instance, Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        final Collection<Method> methods = Methods.create().findAllAndMakeThemAccessible(MethodCriteria.forEntireClassHierarchy().allThoseThatMatch(it -> it.isAnnotationPresent(Inject.class)), clazz).stream().sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
        for (Method method : methods) {
            method.invoke(instance, getParameterInstances(method.getParameterTypes(), method.getParameterAnnotations()));
            method.setAccessible(false);
        }
        return instance;
    }

    public <T> T injectFields(T instance, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Collection<Field> fields = Fields.create().findAllAndMakeThemAccessible(FieldCriteria.forEntireClassHierarchy().allThoseThatMatch(it -> it.isAnnotationPresent(Inject.class)), clazz);
        for (Field field : fields) {
            final Name annotation = field.getAnnotation(Name.class);
            final String name = annotation != null ? annotation.value() : "";
            field.set(instance, field.isAnnotationPresent(Singleton.class) ? getSingleton(name, field.getType()) : inject(syringe.getInjectable(name, field.getType())));
            field.setAccessible(false);
        }
        return instance;
    }

    public <T> T getSingleton(String name, Class<?> type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Storage storage = syringe.getConfigurator().getStorage();
        final Map<Class<?>, ElementList<Object>> singletons = storage.getSingletons();
        ElementList<Object> elementList = singletons.get(type);
        if (elementList == null)
            elementList = new SimpleElementList<>().apply(it -> singletons.put(type, it));
        final Element<Object> element = elementList.get(name);
        if (element != null)
            return (T) element.get();
        final T instance = inject(syringe.getInjectable(name, type));
        elementList.add(name, instance);
        return instance;
    }

    private Object[] getParameterInstances(Class<?>[] types, Annotation[][] annotations) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Object[] arguments = new Object[types.length];
        int i = 0;
        for (Class<?> type : types) {
            final Name annotation = type.getAnnotation(Name.class);
            final String name = annotation != null ? annotation.value() : "";
            final boolean isSingleton = containsSingleton(annotations != null ? annotations[i] : type.getAnnotations());
            arguments[i] = isSingleton ? getSingleton(name, type) : inject(syringe.getInjectable(name, type));
            i++;
        }
        return arguments;
    }

    private boolean containsSingleton(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            System.out.println("Achado: " + annotation.toString());
            if (annotation instanceof Singleton)
                return true;
        }
        return false;
    }

    @Override
    public void close() throws IOException {
        syringe.close();
        syringe = null;
    }

}
