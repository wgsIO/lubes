package oi.ksd.ds.test;

import lombok.Getter;

public class Teste2 implements Testes2 {

    private String text = "ola";

    @Override
    public String getText() {
        return text;
    }

}
