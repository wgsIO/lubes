package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.disposed.SimpleInjector;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleSyringe;

public class Test {

    public static void main(String[] args) {
        try {
            final SimpleInjector injector = new SimpleInjector(new SimpleSyringe(new TesteConfig()).apply(SimpleSyringe::init));
            final Teste inject = injector.inject(Teste.class);
            System.out.println("Foi " + inject.data.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
