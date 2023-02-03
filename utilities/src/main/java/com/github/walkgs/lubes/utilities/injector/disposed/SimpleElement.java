package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.injector.Element;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleElement<T> implements Element, Applicable<SimpleElement> {

    @Getter
    private final String name;
    private final T element;

    @Override
    public T get() {
        return element;
    }

}
