package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.disposed.SimpleAbstractConfigurator;

public class TesteConfig extends SimpleAbstractConfigurator {

    @Override
    public void configure() {
        bind(Data.class).to(Datas.class);
        complete();
    }

}
