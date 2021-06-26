package com.dream.aptdemo;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * function: waiting for add
 *
 * @author zy
 * @since 2021/6/23
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {

    String ip();
    int port() default 3306;
    String name();
}
