package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.injector.disposed.SimpleAbstractConfigurator;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleInjector;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleSyringe;

public class Test {

    public static void main(String[] args) {
        try {
            final SimpleSyringe syringe = new SimpleSyringe(new SimpleAbstractConfigurator() {
                @Override
                public void configure() {

                }
            });

            final SimpleSyringe syringe2 = new SimpleSyringe(new TesteConfig());

            final SimpleInjector injector = new SimpleInjector(syringe.apply(SimpleSyringe::init));

            final SimpleInjector injector2 = new SimpleInjector(syringe2.apply(SimpleSyringe::init));
            injector2.addParent(injector);

            final Teste inject = injector2.inject(Teste.class);
            ((Data) inject.data).text = "porra";
            System.out.println("Foi " + inject.data.getText());
            System.out.println("Text:  " + inject.testes.getText());
            final Teste inject2 = injector.inject(Teste.class);
            System.out.println("Foi2 " + inject2.data.getText());
            System.out.println("Text2:  " + inject2.testes.getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void oi() {

    }

}
