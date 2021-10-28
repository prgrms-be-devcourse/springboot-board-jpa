package com.programmers.springbootboard.annotation;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ArbitraryAuthenticationPrincipal {
}
