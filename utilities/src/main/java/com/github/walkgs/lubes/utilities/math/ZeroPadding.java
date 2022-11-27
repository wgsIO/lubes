package com.github.walkgs.lubes.utilities.math;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ZeroPadding {

    BASE_64("0".repeat(64)),
    BASE_32("0".repeat(32)),
    BASE_16("0".repeat(16));

    private final String value;

    @Override
    public String toString() {
        return value;
    }

    String leftZeroPadding(String text) {
        return value.substring(text.length()) + text;
    }

}
