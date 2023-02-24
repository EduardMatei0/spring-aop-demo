package com.example.aop.aop_final;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Order(2)
@Component
public class LoggingTransactionalAspect {

    @Pointcut("@annotation(javax.transaction.Transactional)")
    private void pointcutTransactionalExpressionOnMethodLevel() {};

    @Pointcut("@within(javax.transaction.Transactional)")
    private void pointcutTransactionalExpressionOnClassLevel() {};

    @Before("pointcutTransactionalExpressionOnMethodLevel() || pointcutTransactionalExpressionOnClassLevel()")
    public void loggTransaction(JoinPoint joinPoint) {
        log.info("Method {} is being executed inside a transaction", joinPoint.getSignature().getName());
    }
}
