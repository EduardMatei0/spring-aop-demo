package com.example.aop.aop_final;

import com.example.aop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Order(1)
@Component
@RequiredArgsConstructor
public class LoggingServiceAspect {

    private final UserService userService;

    @Pointcut("execution(* com.example.aop.service.AzureVirtualMachinesService.*One(..))")
    private void pointcutServiceExpression() {};

    @Before("pointcutServiceExpression()")
    public void loggingAspectMethod(JoinPoint joinPoint) {
        log.info("Calling @Before on method {}", joinPoint.getSignature().getName());
        var currentUser = userService.getCurrentUser();
        log.info("User with id {} and name {} is calling the method {}", currentUser.getId(), currentUser.getName(), joinPoint.getSignature().getName());

        Arrays.stream(joinPoint.getArgs())
                .forEach(arg -> log.info("Method argument is {}", arg));
    }
}
