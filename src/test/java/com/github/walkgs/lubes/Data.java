package com.github.walkgs.lubes;

public class Data implements Datas {

    public String text;
    private int value;

    public Data() {
        this.value = 4;
        this.text = "eae";
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text;
    }

}
