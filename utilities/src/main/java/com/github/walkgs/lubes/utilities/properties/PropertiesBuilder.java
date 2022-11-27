package com.github.walkgs.lubes.utilities.properties;

public interface PropertiesBuilder<T> {

    Object getKey();

    PropertiesBuilder<T> setKey(Object ket);

    T getValue();

    PropertiesBuilder<T> setValue(T value);

    Property<T> build();


}
