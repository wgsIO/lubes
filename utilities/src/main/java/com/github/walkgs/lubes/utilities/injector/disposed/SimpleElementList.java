package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.exceptions.KeyAlreadyExistsException;
import com.github.walkgs.lubes.utilities.injector.Element;
import com.github.walkgs.lubes.utilities.injector.ElementList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleElementList<T> implements ElementList<T>, Applicable<SimpleElementList<T>> {

    private List<Element<T>> elements = new ArrayList<>();

    @Override
    public Element<T> add(String name, T element) {
        if (get(name) != null)
            throw new KeyAlreadyExistsException("The entered name is already attached to an element");
        return new SimpleElement<>(name, element).apply(it -> elements.add(it));
    }

    @Override
    public Element<T> get(String name) {
        for (Element<T> element : elements) {
            if (!element.getName().equals(name))
                continue;
            return element;
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        elements = null;
    }

}
