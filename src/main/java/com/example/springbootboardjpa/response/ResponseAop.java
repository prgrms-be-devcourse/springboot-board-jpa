package com.example.springbootboardjpa.response;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseAop {
    @Around("execution(* com.example.springbootboardjpa.controller.*.*(..))")
    public ApiResponse<Object> response(ProceedingJoinPoint joinPoint) throws Throwable {
        return ApiResponse.ok(joinPoint.proceed());
    }
}
