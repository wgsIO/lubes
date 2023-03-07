package com.github.walkgs.lubes.utilities.callable;

import java.lang.annotation.*;

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Callable {

    String value();

}
