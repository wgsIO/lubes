package com.github.walkgs.lubes.utilities.math;

public class BinaryPadding {

    public static String bin(long value) {
        return ZeroPadding.BASE_64.leftZeroPadding(Long.toBinaryString(value));
    }

}
