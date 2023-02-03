package com.github.walkgs.lubes.utilities.injector;

import java.io.Closeable;

public interface Syringe extends Closeable {

    Configurator getConfigurator();

    <T> Class<? extends T> getInjectable(String name, Class<T> type);


}
