package com.foo.foobar;

public class MyException extends Exception {
    private int counter;
    public MyException(String message) {
        super(message);
    }


    public MyException(int counter) {
        this.counter = counter;
    }
}
