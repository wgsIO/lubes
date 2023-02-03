package com.github.walkgs.lubes.utilities.math;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ZeroPadding {

    BASE_64("0000000000000000000000000000000000000000000000000000000000000000"),
    BASE_32("00000000000000000000000000000000"),
    BASE_16("0000000000000000");

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    String leftZeroPadding(String text) {
        return value.substring(text.length()) + text;
    }

}
