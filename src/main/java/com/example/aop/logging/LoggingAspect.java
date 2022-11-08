package com.example.aop.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {


    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) " +
            " || within(@org.springframework.stereotype.Service *) " +
            " || within(@org.springframework.stereotype.Repository *)" +
            " || within(@org.springframework.security.config.annotation.web.configuration.EnableWebSecurity *)" +
            " || within(@org.springframework.stereotype.Component *)")
    public void springBeanPointCut(){

    }

    @Pointcut("within(com.example.controller..*)" +
            " || within(com.example.service..*)" +
            " || within(com.example.repository.*)" +
            " || within(com.example.config..*)" +
            " || within(com.example.security..*)")
    public void applicationPackagePointCut(){

    }

    @Pointcut("@annotation(com.example.aop.logging.Loggable)")
    public void executeLogging(){

    }


    @Before("executeLogging()")
    public void logMethodCall(JoinPoint joinPoint){
        StringBuilder message = new StringBuilder("Method: ");

        message.append(joinPoint.getSignature().getName());
//                .append("!");
//        Arrays.stream(joinPoint.getArgs())
//                .forEach(arg ->
//                        message.append("args: ").append(arg).append("."));
        log.info(message.toString());
    }


    @AfterThrowing(pointcut = "springBeanPointCut() && applicationPackagePointCut()",throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint,Throwable e){
        StringBuilder message = new StringBuilder("Exception on");
        message.append(joinPoint.getSignature().getDeclaringTypeName())
                .append(".")
                .append(joinPoint.getSignature().getName())
                .append("() with cause = '")
                .append(e.getCause() != null ? e.getCause() : "NULL")
                .append("' and exception = '")
                .append(e.getMessage())
                .append("'");
        log.error(message.toString());
    }

}
