package com.github.walkgs.lubes.utilities.injector.annotation;

import java.lang.annotation.*;

@Inherited
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Name {

    String value() default "";

}
