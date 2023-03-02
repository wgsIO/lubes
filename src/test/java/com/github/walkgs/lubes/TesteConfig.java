package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.disposed.SimpleAbstractConfigurator;
import oi.ksd.ds.test.Teste2;
import oi.ksd.ds.test.Testes2;

public class TesteConfig extends SimpleAbstractConfigurator {

    @Override
    public void configure() {
        bind(Data.class).to(Datas.class);
        bind(new Teste2()).to(Teste2.class).to(Testes2.class);
        complete();
    }

}
