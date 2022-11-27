package com.github.walkgs.lubes.utilities.properties;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
final class UniqueProperty<T> implements Property<T> {

    private final Object key;
    private final T value;

}
