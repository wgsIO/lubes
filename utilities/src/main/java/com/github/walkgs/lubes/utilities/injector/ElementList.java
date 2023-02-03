package com.github.walkgs.lubes.utilities.injector;

import java.io.Closeable;

public interface ElementList<T> extends Closeable {

    Element<T> add(String name, T element);

    Element<T> get(String name);

}
