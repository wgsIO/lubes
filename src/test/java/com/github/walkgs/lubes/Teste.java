package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.annotation.Inject;
import com.github.walkgs.lubes.utilities.injector.annotation.Singleton;
public class Teste {


    @Inject
    @Singleton
    public Datas data;

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
