package com.github.walkgs.lubes.utilities.pagination;

import com.github.walkgs.lubes.utilities.Applicable;

import java.util.Set;

public interface Page<T> extends Applicable<Page<T>>, Iterable<T> {

    int getCapacity();

    int add(T value);

    void set(int index, T value);

    void remove(T value);

    T remove(int index);

    void clear();

    boolean contains(T value);

    T get(int index);

    Set<T> getObjects();

}
