package com.example.SunriseSunset.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**Aspect for logging method execution details in the application.*/
@Aspect
@Component
public class LoggingAspect {

    /** Logger instance for logging method execution details. */
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**Defines a pointcut for all methods in the controller and service packages.*/
    @Pointcut("within(com.example.SunriseSunset.controller..*) || within(com.example.SunriseSunset.service..*)")
    public void applicationPackagePointcut() {}

    /**Logs method entry before execution.*/
    @Before("applicationPackagePointcut()")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Entering: {}.{}()",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());
    }

    /**Logs method exit with the result after successful execution.*/
    @AfterReturning(pointcut = "applicationPackagePointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        logger.info("Exit: {}.{}() with result: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                result != null ? result.toString() : "null");
    }

    /**Logs exceptions thrown during method execution.*/
    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.error("Exception in {}.{}() with cause: {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                e.getMessage() != null ? e.getMessage() : "NULL");
    }
}