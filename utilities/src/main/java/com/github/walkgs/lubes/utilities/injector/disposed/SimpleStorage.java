package com.github.walkgs.lubes.utilities.injector.disposed;

import com.github.walkgs.lubes.utilities.injector.ElementList;
import com.github.walkgs.lubes.utilities.injector.Storage;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class SimpleStorage implements Storage {
    protected final Map<Class<?>, ElementList<Class<?>>> injectables = new HashMap<>();
    protected final Map<Class<?>, ElementList<Object>> singletons = new HashMap<>();

}
