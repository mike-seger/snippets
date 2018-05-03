package test;

import java.io.Serializable;

public final class Hi2 implements Serializable {
    private Hi2() {
        throw new RuntimeException("Cannot do this");
    }

    public void hi() {
        System.out.println("Hi2");
    }

    private long x= 12341313156L;
    private int y = 1122;
    private String z ="sgdfgsdf";
}