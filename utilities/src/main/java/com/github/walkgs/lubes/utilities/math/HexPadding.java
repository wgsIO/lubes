package com.github.walkgs.lubes.utilities.math;

public class HexPadding {

    public static String hex(long value) {
        return ZeroPadding.BASE_16.leftZeroPadding(Long.toHexString(value).toUpperCase());
    }

}
