package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.injector.Configurator;
import com.github.walkgs.lubes.utilities.injector.Element;
import com.github.walkgs.lubes.utilities.injector.ElementList;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

public abstract class SimpleAbstractConfigurator implements Configurator {

    @Getter
    private final SimpleStorage storage = new SimpleStorage();
    private final Set<ElementBinder> queues = new HashSet<>();

    public ElementBinder bind(Object instance) {
        return new ElementBinder(Element.DEFAULT_NAME, instance, instance.getClass()).apply(it -> queues.add(it));
    }

    public ElementBinder bind(Class<?> clazz) {
        return new ElementBinder(Element.DEFAULT_NAME, null, clazz).apply(it -> queues.add(it));
    }

    public ElementBinder bind(String name, Object instance) {
        return new ElementBinder(name, instance, instance.getClass()).apply(it -> queues.add(it));
    }

    public ElementBinder bind(String name, Class<?> clazz) {
        return new ElementBinder(name, null, clazz).apply(it -> queues.add(it));
    }

    protected void complete() {
        for (ElementBinder queue : queues) {
            if (queue.instance == null) {
                for (Class<?> implementation : queue.implementations) {
                    ElementList<Class<?>> elementList = storage.getInjectables().get(implementation);
                    if (elementList == null)
                        elementList = new SimpleElementList<Class<?>>().apply(it -> storage.injectables.put(implementation, it));
                    elementList.add(queue.name, queue.clazz);
                }
                continue;
            }
            for (Class<?> implementation : queue.implementations) {
                ElementList<Object> elementList = storage.getSingletons().get(implementation);
                if (elementList == null)
                    elementList = new SimpleElementList<>().apply(it -> storage.singletons.put(implementation, it));
                elementList.add(queue.name, queue.clazz);
            }
        }
    }

}
