package com.programmers.springbootboard.annotation;

import javax.persistence.Inheritance;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inheritance
public @interface ThreadSafety {
}
