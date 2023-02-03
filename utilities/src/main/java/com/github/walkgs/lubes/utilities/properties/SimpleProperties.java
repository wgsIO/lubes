package com.github.walkgs.lubes.utilities.properties;

import com.github.walkgs.lubes.utilities.Applicable;
import com.github.walkgs.lubes.utilities.ArraysUtils;
import com.github.walkgs.lubes.utilities.exceptions.ArrayIndexLimitException;
import com.github.walkgs.lubes.utilities.exceptions.KeyAlreadyExistsException;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class SimpleProperties implements Properties, Applicable<SimpleProperties> {

    private static final int DEFAULT_SIZE = 0;
    private static final int DEFAULT_LIMIT = 0;

    private Property[] properties = new Property[DEFAULT_SIZE];
    private final int limit;

    public SimpleProperties() {
        limit = DEFAULT_LIMIT;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> get(Object key) {
        for (Property<?> property : properties) {
            if (property != null && property.getKey().equals(key))
                return (Property<T>) property;
        }
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> get(int index) {
        if (properties.length < index)
            throw new ArrayIndexOutOfBoundsException(index);
        return properties[index];
    }

    @Override
    public void set(int index, Property<?> property) {
        if (properties.length < index)
            throw new ArrayIndexOutOfBoundsException(index);
        properties[index] = property;
    }

    @Override
    public void set(int index, PropertiesBuilder<?> builder) {
        if (properties.length < index)
            throw new ArrayIndexOutOfBoundsException(index);
        properties[index] = builder.build();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> void add(Property<T> property) {
        for (Property<?> $property : properties) {
            if ($property != null && $property.getKey().equals(property.getKey()))
                throw new KeyAlreadyExistsException();
        }
        final int lastIndex = properties.length;
        if (limit != 0 && lastIndex >= limit)
            throw new ArrayIndexLimitException();
        for (int index = 0; index < properties.length; index++) {
            final Property<T> $property = properties[index];
            if ($property != null)
                continue;
            properties[index] = property;
            return;
        }
        properties = Arrays.copyOf(properties, lastIndex + 1);
        properties[lastIndex] = property;
    }

    @Override
    public <T> Property<T> add(Object key, T value) {
        return add(new UniquePropertiesBuilder<T>().setKey(key).setValue(value));
    }

    @Override
    public <T> Property<T> add(PropertiesBuilder<T> builder) {
        final Property<T> property = builder.build();
        add(property);
        return property;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> remove(Object key) {
        for (int index = 0; index < properties.length; index++) {
            final Property<T> property = properties[index];
            if (property != null && property.getKey().equals(key)) {
                properties = ArraysUtils.copy(properties, 0, properties.length - 1, index);
                return property;
            }
        }
        return null;
    }

    @Override
    public <T> boolean remove(Property<T> property) {
        for (int index = 0; index < properties.length; index++) {
            final Property<?> $property = properties[index];
            if ($property != null && ($property == property || $property.getKey().equals(property.getKey()))) {
                properties = ArraysUtils.copy(properties, 0, properties.length - 1, index);
                return true;
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Property<T> remove(int index) {
        if (properties.length < index)
            throw new ArrayIndexOutOfBoundsException(index);
        final Property<T> property = properties[index];
        properties = ArraysUtils.copy(properties, 0, properties.length - 1, index);
        return property;
    }

    @Override
    public boolean contains(Object key) {
        for (int index = 0; index < properties.length; index++) {
            final Property<?> $property = properties[index];
            if ($property != null && $property.getKey().equals(key)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <T> boolean contains(Property<T> property) {
        return contains(property.getKey());
    }

    @Override
    public int size() {
        return properties.length;
    }

    @Override
    public void clear() {
        Arrays.fill(properties, null);
    }

    @Override
    public void clear(Class<?> keyType) {
        for (int index = 0; index < properties.length; index++) {
            final Property<?> property = properties[index];
            if (property != null && property.getKey().getClass().isAssignableFrom(keyType))
                properties[index] = null;
        }
    }

    @Override
    public Set<Property<?>> all() {
        final Stream<Property<?>> stream = Arrays.stream(properties);
        return stream.collect(Collectors.toSet());
    }

    @Override
    public Set<Property<?>> all(Class<?> keyType) {
        final Stream<Property<?>> stream = Stream.of(this.properties);
        final Set<Property<?>> properties = stream.filter(property -> keyType != null && property != null && property.getKey().getClass().isAssignableFrom(keyType)).collect(Collectors.toSet());
        stream.close();
        return properties;
    }

    @Override
    public Iterator<Property<?>> iterator() {
        return all().iterator();
    }

}
