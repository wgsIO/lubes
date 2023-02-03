package com.github.walkgs.lubes.utilities.injector.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {

    int value() default 1;

}
