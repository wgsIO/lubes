package com.github.walkgs.lubes;

import com.github.walkgs.lubes.utilities.ArraysUtils;
import com.github.walkgs.lubes.utilities.identifiers.SnowIdentifier;
import com.github.walkgs.lubes.utilities.math.BinaryPadding;
import com.github.walkgs.lubes.utilities.math.HexPadding;
import com.github.walkgs.lubes.utilities.math.Math;
import com.github.walkgs.lubes.utilities.math.ZeroPadding;
import com.github.walkgs.lubes.utilities.pagination.Page;
import com.github.walkgs.lubes.utilities.pagination.SimplePage;
import com.github.walkgs.lubes.utilities.properties.Properties;
import com.github.walkgs.lubes.utilities.properties.Property;
import com.github.walkgs.lubes.utilities.properties.SimpleProperties;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.Random;

public class Main {
    private static final Random RANDOM = new Random();
    private static final Properties PROPERTIES = new SimpleProperties();

    private static <T> void copyAndExclude(T[] objects, T[] fake, int start, int end, int exclude) {
        int last = start;
        for (int index = start; index < end; index++) {
            if (index == exclude)
                continue;
            fake[last] = objects[index];
            last++;
        }
    }

    @SneakyThrows
    public static void main(String[] args) {


        Object[] objects = new Object[10];
        int max3 = 10;
        for (int index = 0; index < max3; index++) {
            objects[index] = "key" + index;
        }
        objects = ArraysUtils.remove(objects, 5);
        System.out.println("Obj: " + Arrays.toString(objects));

        if (true)
            return;
        /*
        Object[] objects = new Object[10];
        int max3 = 10;
        for (int index = 0; index < max3; index++) {
            objects[index] = "key" + index;
        }
        System.out.println("Obj: " + Arrays.toString(objects));

         */

        //Remove
        int idx = 3;
        final Object[] anotherArray = ArraysUtils.copy(objects, 0, objects.length, 5);
        //copyAndExclude(objects, anotherArray, 0, objects.length, 6);
        //System.arraycopy(objects, idx + 1,
        //        anotherArray, 3,
        //        objects.length - idx - 1);

        System.out.println("Value: " + (objects.length - idx - 1));

        objects = anotherArray;

        System.out.println("Obj2: " + Arrays.toString(objects));

        if (true)
            return;

        final Page<String> page = new SimplePage<>();
        int max2 = 10;
        for (int index = 0; index < max2; index++) {
            //page.add("key" + RANDOM.nextInt(30) + index * RANDOM.nextInt(30));
            page.add("key" + index);
            System.out.println("Added Values: " + "key" + index);
        }
        page.remove(3);
        System.out.println("Page Values: " + page);

        if (true)
            return;

        System.out.println("Epoch: " + System.currentTimeMillis());
        for (int i = 0; i < 10; i++)
            System.out.println("ID: " + new SnowIdentifier(i, 5).nextId());

        System.out.println("Padding 64: " + ZeroPadding.BASE_64);
        System.out.println("Padding 32: " + ZeroPadding.BASE_32);
        System.out.println("Padding 16: " + ZeroPadding.BASE_16);

        System.out.println("Binary: " + HexPadding.hex(1000));
        System.out.println("Hex: " + BinaryPadding.bin(1000));

        System.out.println("Diode: " + Math.diode(388, 63));

        try {
            int max = 10;
            for (int index = 0; index < max; index++) {
                PROPERTIES.add("key" + index, index * RANDOM.nextInt(30));
            }
            final int initial = RANDOM.nextInt(max - ((max / 100) * 5));
            final int last = RANDOM.nextInt(0);
            System.out.println("Initial: " + initial + " | Last " + last);
            for (int index = initial; index < last; index++) {
                final Property<Object> $property = PROPERTIES.remove(0);
                System.out.println("Removed: " + $property.getKey() + " | Value: " + $property.getValue());
            }
            for (Property property : PROPERTIES.all()) {
                System.out.println(property);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
