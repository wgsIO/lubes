package com.github.walkgs.lubes.utilities.math;

public final class Math {

    public static long diode(int offset, int length) {
        assert offset < 0 || length < 0 || (offset + length) > 64 : "bits ranges: [0, 64)";
        if (length == 0)
            return 0L;
        if (length == 64)
            return -1L;
        int lb = 64 - offset;
        int rb = 64 - (offset + length);
        return (-1L << lb) ^ (-1L << rb);
    }

    public static long safeDiode(long offset, long length) {
        int lb = (int) (64 - offset);
        int rb = (int) (64 - (offset + length));
        return (-1L << lb) ^ (-1L << rb);
    }

}
