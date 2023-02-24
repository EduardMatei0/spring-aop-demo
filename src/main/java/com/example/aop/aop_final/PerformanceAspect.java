package com.example.aop.aop_final;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Slf4j
//@Aspect
//@Component
public class PerformanceAspect {

    @Pointcut("execution(* com.example.aop.*.*.*(..))")
    private void allMethodsExpression() {}

    @Pointcut("execution(* com.example.aop.config.*.*(..))")
    private void excludeConfigExpression() {}

    @Pointcut("execution(* com.example.aop.external.*.*(..))")
    private void excludeExternalExpression() {}

    @Around("allMethodsExpression() && !excludeConfigExpression() && !excludeExternalExpression()")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        Instant start = Instant.now();
        try {
            Object result = proceedingJoinPoint.proceed();
            Instant end = Instant.now();
            log.info("@Around advice. Method {} executed in {} milliseconds", proceedingJoinPoint.getSignature(), Duration.between(start, end).toMillis());
            return result;
        } catch (Exception e) {
            Instant end = Instant.now();
            log.info("@Around advice Exception. Method {} executed in {} milliseconds", proceedingJoinPoint.getSignature(), Duration.between(start, end).toMillis());
            throw e;
        }

    }
}
