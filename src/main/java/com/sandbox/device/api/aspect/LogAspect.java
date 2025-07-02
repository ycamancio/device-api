package com.sandbox.device.api.aspect;

import com.sandbox.device.api.annotation.LogEvent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class LogAspect {


    @Around("@annotation(com.sandbox.device.api.annotation.LogEvent)")
    public Object loggingMethodExecutionWithLogAnnotation(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        String methodName = methodSignature.getName();
        String className = methodSignature.getDeclaringTypeName();

        int eventId = methodSignature.getMethod().getAnnotation(LogEvent.class).id();
        String description = methodSignature.getMethod().getAnnotation(LogEvent.class).description();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Object object = proceedingJoinPoint.proceed();

        stopWatch.stop();

        Logger logger = LoggerFactory.getLogger(className);
        logger.info(buildLogMessage(methodName, eventId, description, stopWatch.getTotalTimeMillis()));

        return object;
    }

    private String buildLogMessage(String methodName, int eventId, String description, long duration){

        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Method: ").append(methodName).append("()");
        sBuilder.append(" -> Event_id= ").append(eventId).append(", Event_description= ").append(description);
        sBuilder.append(" - Duration(ms):").append(duration);

        return sBuilder.toString();
    }


}
