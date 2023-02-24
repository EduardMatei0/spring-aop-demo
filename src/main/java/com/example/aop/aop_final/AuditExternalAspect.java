package com.example.aop.aop_final;

import com.example.aop.dao.AuditExternalDao;
import com.example.aop.model.AuditExternal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;


@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class AuditExternalAspect {

    private final AuditExternalDao auditExternalDao;

    @Pointcut("execution(* com.example.aop.service.ConnectorService.call*(..))")
    private void pointcutExternalApiExpression() {};

    @AfterReturning(pointcut = "pointcutExternalApiExpression()", returning = "result")
    public void successfulResponse(JoinPoint joinPoint, Object result) {
        log.info("Calling @AfterReturning on method {}", joinPoint.getSignature().getName());
        var auditExternal = buildObject(joinPoint, result, HttpStatus.OK);
        saveToDb(auditExternal);
    }

    @AfterThrowing(pointcut = "pointcutExternalApiExpression()", throwing = "result")
    public void failedResponse(JoinPoint joinPoint, HttpStatusCodeException result) {
        log.info("Calling @AfterThrowing on method {}", joinPoint.getSignature().getName());
        var auditExternal = buildObject(joinPoint, result.getResponseBodyAsString(), result.getStatusCode());
        saveToDb(auditExternal);
    }

    @After("pointcutExternalApiExpression()")
    public void cleanUp(JoinPoint joinPoint) {
        log.info("Calling @After on method {}", joinPoint.getSignature().getName());

        log.info("Doing some cleanup......");
    }

    public void saveToDb(AuditExternal auditExternal) {
        auditExternalDao.saveToDb(auditExternal);
    }

    private AuditExternal buildObject(JoinPoint joinPoint, Object result, HttpStatus httpStatus) {
        AuditExternal auditExternal = new AuditExternal();
        auditExternal.setMethodName(joinPoint.getSignature().getName());
        auditExternal.setReqBody(convertObjectToJsonString(joinPoint.getArgs()[0]));
        auditExternal.setResponse(convertObjectToJsonString(result));
        auditExternal.setStatusCode(httpStatus.value());
        return auditExternal;
    }

    private String convertObjectToJsonString(Object result) {
        try {
            ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
            return ow.writeValueAsString(result);
        } catch (Exception e) {
            log.info("Something wrong with converting to json", e);
        }
        return result.toString();
    }
}
