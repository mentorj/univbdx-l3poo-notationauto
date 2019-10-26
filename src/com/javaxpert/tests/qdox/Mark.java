package com.javaxpert.tests.qdox;
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Mark {
    public int value() default 0;
}

