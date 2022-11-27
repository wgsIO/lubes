package com.github.walkgs.lubes.utilities.flags;

public class Flags {

    public static int set(int source, int flag) {
        return source | flag;
    }

    public static int unset(int source, int flag) {
        return source & ~flag;
    }

    public static boolean has(int source, int flag) {
        return (source & flag) == flag;
    }

    public static int flip(int source, int flag) {
        return source & ~flag;
    }

    public static int mask(int source, int mask) {
        return source & mask;
    }

}
