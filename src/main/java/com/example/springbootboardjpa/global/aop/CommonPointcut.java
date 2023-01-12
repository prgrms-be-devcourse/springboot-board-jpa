package com.example.springbootboardjpa.global.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

  @Pointcut("execution(public * com.example.springbootboardjpa..*.*(..))")
  public void packagePublicMethodPointcut() {
  }
}
