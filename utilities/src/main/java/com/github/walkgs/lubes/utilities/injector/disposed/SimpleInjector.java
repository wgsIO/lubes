package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.injector.*;
import com.github.walkgs.lubes.utilities.injector.annotation.Inject;
import com.github.walkgs.lubes.utilities.injector.annotation.Name;
import com.github.walkgs.lubes.utilities.injector.annotation.Singleton;
import com.github.walkgs.lubes.utilities.seekers.Constructors;
import com.github.walkgs.lubes.utilities.seekers.Fields;
import com.github.walkgs.lubes.utilities.seekers.Methods;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class SimpleInjector implements Injector {

    private static final SimplePriorityComparator PRIORITY_COMPARATOR = new SimplePriorityComparator();

    private Syringe syringe;
    private Collection<Injector> parents;

    public SimpleInjector(Syringe syringe) {
        this.syringe = syringe;
        this.parents = new ConcurrentLinkedQueue<>();
    }

    @Override
    public <T> T inject(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {

        T instance = injectViaConstructor(clazz);

        injectViaFields(instance, clazz);

        return injectViaMethods(instance, clazz);
    }

    public <T> T injectViaConstructor(Class<?> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final List<Constructor<?>> constructors = Constructors.findDeclared(clazz).filter(setAccessibleAndFilter()).sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
        if (constructors.size() == 0)
            return (T) clazz.newInstance();
        else {
            final Constructor<?> constructor = constructors.get(0);
            final boolean singletonAll = constructor.isAnnotationPresent(Singleton.class);
            return (T) constructor.newInstance(getParameterInstances(constructor.getParameterTypes(), constructor.getParameterAnnotations(), singletonAll));
        }
    }

    public <T> T injectViaMethods(T instance, Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        final Collection<Method> methods = Methods.findDeclared(clazz).filter(setAccessibleAndFilter()).sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
        for (Method method : methods) {
            injectAtMethod(instance, method);
        }
        return instance;
    }

    public <T> void injectAtMethod(T instance, Method method) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        final boolean singletonAll = method.isAnnotationPresent(Singleton.class);
        method.invoke(instance, getParameterInstances(method.getParameterTypes(), method.getParameterAnnotations(), singletonAll));
        method.setAccessible(false);
    }

    @Override
    public <T> T injectViaFields(T instance, Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        return injectViaFields(instance, clazz, false);
    }

    public <T> T injectViaFields(T instance, Class<T> clazz, boolean singletonAll) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Collection<Field> fields = Fields.findDeclared(clazz).filter(setAccessibleAndFilter()).sorted(PRIORITY_COMPARATOR).collect(Collectors.toList());
        for (Field field : fields) {
            if (singletonAll) {
                injectAtField(instance, field, true);
                continue;
            }
            injectAtField(instance, field);
        }
        return instance;
    }

    public <T> void injectAtField(T instance, Field field) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        //final Name annotation = field.getAnnotation(Name.class);
        //final String name = annotation != null ? annotation.value() : Element.DEFAULT_NAME;
        //field.set(instance, field.isAnnotationPresent(Singleton.class) ? getSingleton(name, field.getType()) : inject(syringe.getInjectable(name, field.getType())));
        //field.setAccessible(false);
        injectAtField(instance, field, field.isAnnotationPresent(Singleton.class));
    }

    public <T> void injectAtField(T instance, Field field, boolean singleton) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Name annotation = field.getAnnotation(Name.class);
        final String name = annotation != null ? annotation.value() : Element.DEFAULT_NAME;
        field.set(instance, singleton ? getSingleton(name, field.getType()) : inject(syringe.getInjectable(name, field.getType())));
        field.setAccessible(false);
    }

    public <T> T getSingleton(String name, Class<?> type) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final HashSet<Injector> injectors = new HashSet<>();
        injectors.add(this);
        getParentsInjectors(this, injectors);
        Map<Class<?>, ElementList<Object>> map = null;
        for (Injector injector : injectors) {
            final Syringe syringe = injector.getSyringe();
            final Configurator configurator = syringe.getConfigurator();
            final Storage storage = configurator.getStorage();
            final Map<Class<?>, ElementList<Object>> singletons = storage.getSingletons();
            final ElementList<Object> elementList = singletons.get(type);
            if (elementList == null) {
                if (map == null)
                    map = singletons;
                continue;
            }
            final Element<Object> element = elementList.get(name);
            if (element == null)
                continue;
            return (T) element.get();
        }
        final Map<Class<?>, ElementList<Object>> singletons = map;
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

    private Object[] getParameterInstances(Class<?>[] types, Annotation[][] annotations, boolean singletonAll) throws InvocationTargetException, InstantiationException, IllegalAccessException {
        final Object[] arguments = new Object[types.length];
        int i = 0;
        for (Class<?> type : types) {
            final Name annotation = type.getAnnotation(Name.class);
            final String name = annotation != null ? annotation.value() : Element.DEFAULT_NAME;
            final boolean isSingleton = singletonAll || containsSingleton(annotations != null ? annotations[i] : type.getAnnotations());
            arguments[i] = isSingleton ? getSingleton(name, type) : inject(syringe.getInjectable(name, type));
            i++;
        }
        return arguments;
    }

    private boolean containsSingleton(Annotation[] annotations) {
        for (Annotation annotation : annotations) {
            if (annotation instanceof Singleton)
                return true;
        }
        return false;
    }

    private void getParentsInjectors(Injector injector, HashSet<Injector> set) {
        for (Injector parent : injector.getParents()) {
            if (set.contains(parent))
                continue;
            set.add(parent);
            getParentsInjectors(parent, set);
        }
    }

    @Override
    public void close() throws IOException {
        for (Injector parent : parents)
            removeParent(parent);
        syringe.close();
        syringe = null;
    }

    @Override
    public void addParent(Injector parenting) {
        this.parents.add(parenting);
        parenting.getParents().add(this);
        syringe.addParent(parenting.getSyringe());
    }

    @Override
    public void removeParent(Injector parenting) {
        this.parents.remove(parenting);
        parenting.getParents().remove(this);
        syringe.removeParent(parenting.getSyringe());
    }

    private Predicate<AnnotatedElement> setAccessibleAndFilter() {
        return it -> {
            final boolean isInject = it.isAnnotationPresent(Inject.class);
            if (isInject) {
                final AccessibleObject object = (AccessibleObject) it;
                if (!object.isAccessible())
                    object.setAccessible(true);
            }
            return isInject;
        };
    }

}
