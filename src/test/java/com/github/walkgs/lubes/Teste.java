package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.annotation.Inject;
import com.github.walkgs.lubes.utilities.injector.annotation.Singleton;
import oi.ksd.ds.test.Testes2;

public class Teste {


    @Inject
    //@Singleton
    public Datas data;

    @Inject
    @Singleton
    public Testes2 testes;
    /*
    @Inject
    public com.github.walkgs.lubes.Teste(@Singleton com.github.walkgs.lubes.Datas data) {
        this.data = data;
    }

    public void setData(com.github.walkgs.lubes.Datas data) {
        this.data = data;
    }

     */

}
