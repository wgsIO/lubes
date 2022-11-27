package com.github.walkgs.lubes.utilities;

import java.lang.reflect.Array;
import java.util.Objects;

public final class ArraysUtils {

    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    public static final Class[] EMPTY_CLASS_ARRAY = new Class[0];

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static <T> int size(T[] array) {
        return array == null ? 0 : array.length;
    }

    public static <T> T[] clone(final T[] array) {
        Objects.requireNonNull(array);
        return array.clone();
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] addAll(final T[] original, final T[] copy) {
        Objects.requireNonNull(original);
        Objects.requireNonNull(copy);
        final Class<? extends Object[]> clazz = original.getClass();
        final T[] newArray = (T[]) Array.newInstance(clazz.getComponentType(), original.length + copy.length);
        System.arraycopy(original, 0, newArray, 0, original.length);
        System.arraycopy(copy, 0, newArray, original.length, copy.length);
        return newArray;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(T[] array, int index)
    {
        final int length = array.length;
        final Class<? extends Object[]> clazz = array.getClass();
        final T[] newArray = (T[]) Array.newInstance(clazz.getComponentType(), length - 1);
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, length - index - 1);
        return newArray;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] copy(T[] original, int start, int end) {
        int length = end - start;
        if (length < 0)
            throw new IllegalArgumentException(start + " > " + end);
        final Class<? extends Object[]> clazz = original.getClass();
        final T[] clone = (clazz == Object[].class)
                ? (T[]) new Object[length]
                : (T[]) Array.newInstance(clazz.getComponentType(), length);
        System.arraycopy(original, start, clone, 0,
                Math.min(original.length - start, length));
        return clone;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] copy(T[] original, int start, int end, int exclude) {
        int length = end - start;
        if (length < 0)
            throw new IllegalArgumentException(start + " > " + end);
        Objects.requireNonNull(original);
        if (exclude > original.length || exclude < 0)
            throw new IndexOutOfBoundsException();
        final Class<? extends Object[]> clazz = original.getClass();
        final T[] clone = (clazz == Object[].class)
                ? (T[]) new Object[length - 1]
                : (T[]) Array.newInstance(clazz.getComponentType(), length - 1);
        int lastIndex = 0;
        for (int index = start; index < end; index++) {
            if (index == exclude)
                continue;
            clone[lastIndex] = original[index];
            lastIndex++;
        }
        return clone;
    }

}
