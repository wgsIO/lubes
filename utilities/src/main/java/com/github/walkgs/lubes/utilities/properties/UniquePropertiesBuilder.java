package com.github.walkgs.lubes.utilities.properties;

import com.github.walkgs.lubes.utilities.Applicable;
import lombok.Getter;

@Getter
public class UniquePropertiesBuilder<T> implements PropertiesBuilder<T>, Applicable<UniquePropertiesBuilder<T>> {

    private Object key;
    private T value;

    @Override
    public PropertiesBuilder<T> setKey(Object key) {
        this.key = key;
        return this;
    }

    @Override
    public PropertiesBuilder<T> setValue(T value) {
        this.value = value;
        return this;
    }

    @Override
    public Property<T> build() {
        return new UniqueProperty<>(key, value);
    }

}
