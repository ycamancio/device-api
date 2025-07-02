package com.sandbox.device.api.aspect;

import com.sandbox.device.api.annotation.Auditable;
import com.sandbox.device.api.annotation.LogEvent;
import com.sandbox.device.api.enums.AuditAction;
import com.sandbox.device.api.service.AuditService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class AuditAspect {

    private final AuditService auditService;

    public AuditAspect(AuditService auditService) {
        this.auditService = auditService;
    }

    @AfterReturning("@annotation(com.sandbox.device.api.annotation.Auditable)")
    public void auditingEvents(JoinPoint joinPoint) throws Throwable {

        String username = generateRandomUserName();
        AuditAction action = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Auditable.class).action();
        String description = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(Auditable.class).description();

       auditService.auditAction(action, description, username);
    }

    private String generateRandomUserName() {

        String[] users = {"john", "mark", "matthew", "luke"};
        int randomIndex = (int) (Math.random() * users.length);
        return users[randomIndex];
    }

}
