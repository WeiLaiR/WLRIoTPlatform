package com.wei.iotplatformuserservice.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorityAnnotation {
    int sign() default 0;
}
