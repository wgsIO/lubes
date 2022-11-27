package com.github.walkgs.lubes.utilities.pagination;

import com.github.walkgs.lubes.utilities.ArraysUtils;
import com.github.walkgs.lubes.utilities.exceptions.ArrayIndexLimitException;
import com.github.walkgs.lubes.utilities.exceptions.KeyAlreadyExistsException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
public class SimplePage<T> implements Page<T> {

    private static final int DEFAULT_PAGE_CAPACITY = 0;

    @Getter
    private final int capacity;

    private Object[] objects = new Object[DEFAULT_PAGE_CAPACITY];

    public SimplePage() {
        this.capacity = DEFAULT_PAGE_CAPACITY;
    }

    @Override
    public int add(T value) {
       final int lastLength = objects.length;
        if (capacity != 0 && lastLength >= capacity)
            throw new ArrayIndexLimitException();
        for (final Object object : getObjects()) {
            if (object != null && object.equals(value))
                throw new KeyAlreadyExistsException();
        }
        //Objects.requireNonNull(value);
        for (int index = 0; index < lastLength; index++) {
            final Object object = objects[index];
            if (object != null)
                continue;
            objects[index] = value;
            return index;
        }
        objects = Arrays.copyOf(objects, lastLength + 1);
        objects[lastLength] = value;
        return lastLength;
    }

    @Override
    public void set(int index, T value) {
        if (objects.length < index)
            throw new ArrayIndexOutOfBoundsException(index);
        objects[index] = value;
    }

    @Override
    public void remove(T value) {
        final int index = indexOfRange(value, 0, objects.length);
        remove(index);
    }

    @Override
    public T remove(int index) {
        final T object = get(index);
        this.objects = ArraysUtils.copy(objects, 0, objects.length - 1, index);
        return object;
    }

    private int indexOfRange(Object object, int start, int end) {
        final Object[] objects = this.objects;
        final boolean isNull = object == null;
        for (int index = start; index < end; index++) {
            final Object obj = objects[index];
            if (isNull) {
                if (obj == null)
                    return index;
                continue;
            } else
                if (object.equals(obj))
                    return index;
        }
        return -1;
    }

    @Override
    public void clear() {
        objects = new Object[DEFAULT_PAGE_CAPACITY];
    }

    @Override
    public boolean contains(T value) {
        for (final Object object : getObjects())
            if (object.equals(value))
                return true;
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (capacity != 0 && index > capacity)
            throw new ArrayIndexOutOfBoundsException(index);
        return (T) objects[index];
    }

    @Override
    @SuppressWarnings("unchecked")
    public Set<T> getObjects() {
        final Set<T> set = new HashSet<>();
        for (Object object : objects)
            //if (object != null)
                set.add((T) object);
        return set;
    }

    @Override
    public Iterator<T> iterator() {
        return getObjects().iterator();
    }

    @Override
    public String toString() {
        return "Page[Capacity: "+ capacity +", Values: "+ Arrays.toString(objects) +"]";
    }

}
