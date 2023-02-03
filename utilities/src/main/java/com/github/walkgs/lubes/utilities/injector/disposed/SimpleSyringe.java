package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.Strings;
import com.github.walkgs.lubes.utilities.injector.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.IOException;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SimpleSyringe implements Syringe, Applicable<SimpleSyringe> {
    private Configurator configurator;

    public void init() { configurator.configure(); }

    @Override
    public <T> Class<? extends T> getInjectable(String name, Class<T> type) {
        final Storage storage = configurator.getStorage();
        final Map<Class<?>, ElementList<Class<?>>> injectables = storage.getInjectables();
        final ElementList<Class<?>> elementList = injectables.get(type);
        if (elementList == null)
            throw new IllegalArgumentException("No inject binded for type: " + type);
        final Element<Class<?>> element = elementList.get(Strings.isNullOrBlank(name) ? Element.DEFAULT_NAME : name);
        if (element == null)
            throw new IllegalArgumentException("No inject binded for type: " + type);
        return element.get().asSubclass(type);
    }

    @Override
    public void close() throws IOException {
        configurator = null;
    }

}
