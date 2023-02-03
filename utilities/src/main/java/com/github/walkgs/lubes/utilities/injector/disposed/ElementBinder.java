package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.Applicable;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
public class ElementBinder implements Applicable<ElementBinder> {

    protected final String name;
    protected final Object instance;
    protected final Class<?> clazz;
    protected final List<Class<?>> implementations = new ArrayList<>();

    public ElementBinder to(Class<?>... to) {
        for (Class<?> clazz : to) {
            if (implementations.contains(to))
                continue;
            implementations.add(clazz);
        }
        return this;
    }

}
