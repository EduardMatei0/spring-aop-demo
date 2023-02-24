package com.example.aop.aop;

import com.example.aop.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AzureVMAspect {

    private final UserService userService;

    @Pointcut("execution(* com.example.aop.service.AzureVirtualMachinesService.*One(..))")
    private void crudPointcut() {};

    @Before("crudPointcut()")
    public void loggingUser(JoinPoint joinPoint) {
        var currentUser = userService.getCurrentUser();
        log.info("User with id {} and name {} is calling the method {}", currentUser.getId(), currentUser.getName(), joinPoint.getSignature().getName());

        Arrays.stream(joinPoint.getArgs())
                .forEach(arg -> log.info("Method argument is {}", arg));
    }
}
