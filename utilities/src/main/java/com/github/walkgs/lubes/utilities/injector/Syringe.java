package com.github.walkgs.lubes.utilities.injector;

import com.github.walkgs.lubes.utilities.Parenting;

import java.io.Closeable;

public interface Syringe extends Closeable, Parenting<Syringe> {

    Configurator getConfigurator();

    <T> Class<? extends T> getInjectable(String name, Class<T> type);


}
