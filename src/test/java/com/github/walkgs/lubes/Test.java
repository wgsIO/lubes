package com.github.walkgs.lubes;

import com.github.walkgs.lubes.progressTest.ColoredBar;
import com.github.walkgs.lubes.progressTest.ColoredWholeBar;
import com.github.walkgs.lubes.progressTest.SimpleProgress;
import com.github.walkgs.lubes.utilities.identifiers.CodeGenerator;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleAbstractConfigurator;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleInjector;
import com.github.walkgs.lubes.utilities.injector.disposed.SimpleSyringe;

import java.util.ArrayList;
import java.util.UUID;

public class Test {

    public static void main(String[] args) {


        double size = 1D / 3;
        double value = 10.5;
        final int rr = ((Number) value).intValue();
        System.out.println("Result: " + rr);
        System.out.println("Broken: " + (value - rr));
        System.out.println("Size: " + size);
        System.out.println("RR: " + ((value - rr) % size) * 10);
        System.out.println("Repeat: " + (value / size));

        //if (true)
        //    return;
        System.out.println("   ^^   ");

        double a = 90.8;
        final SimpleProgress coloredPercent = new SimpleProgress( new ColoredWholeBar());
        System.out.println("percent: " + coloredPercent.getFormat(100, 100, a));

        if ( true)
            return;

        final ArrayList<UUID> id = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            UUID uuid = UUID.randomUUID();
            while (id.contains(uuid)) {
                uuid = UUID.randomUUID();
            }
            id.add(uuid);
            System.out.println("ID: " + uuid);
        }


        if ( true)
            return;
        final CodeGenerator generator = new CodeGenerator("0123456789", "Nick", 16);
        final ArrayList<String> codes = new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            generator.update();
            final String result = generator.getResult();
            if (codes.contains(result)) {
                System.out.println("REPETIUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUUU");
                continue;
            }
            //System.out.println("Código: " + result);
            //System.out.println("Seed: " + generator.getSeed());
            System.out.println("Param: " + generator.getCode() + " | " + generator.getLast());
            codes.add(result);
        }

        if (true)
            return;
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
            System.out.println("Text:  " + inject.getTestestets().getText());
            final Teste inject2 = injector.inject(Teste.class);
            System.out.println("Foi2 " + inject2.data.getText());
            System.out.println("Text2:  " + inject2.getTestestets().getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void oi() {

    }

}
