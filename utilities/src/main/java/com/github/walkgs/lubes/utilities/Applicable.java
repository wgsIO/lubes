package com.github.walkgs.lubes.utilities;

import java.util.function.Consumer;

public interface Applicable<T extends Applicable> {

    @SuppressWarnings("unchecked")
    default T apply(Consumer<T> apply) {
        final T object = (T) this;
        apply.accept(object);
        return object;
    }

}
