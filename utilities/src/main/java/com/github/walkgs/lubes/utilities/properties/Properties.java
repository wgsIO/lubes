package com.github.walkgs.lubes.utilities.properties;

import java.util.Set;

public interface Properties extends Iterable<Property<?>> {

    <T> Property<T> get(Object key);

    <T> Property<T> get(int index);

    void set(int index, Property<?> property);

    void set(int index, PropertiesBuilder<?> builder);

    <T> void add(Property<T> property);

    <T> Property<T> add(Object key, T value);

    <T> Property<T> add(PropertiesBuilder<T> builder);

    <T> Property<T> remove(Object key);

    <T> boolean remove(Property<T> property);

    <T> Property<T> remove(int index);

    boolean contains(Object key);

    <T> boolean contains(Property<T> property);

    int size();

    void clear();

    void clear(Class<?> keyType);

    Set<Property<?>> all();

    Set<Property<?>> all(Class<?> keyType);

}
