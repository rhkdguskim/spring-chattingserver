package com.chat.chattingserver.common.aop.annotation;

import com.chat.chattingserver.common.dto.TraceStatus;
import com.chat.chattingserver.common.util.LogTrace;
import com.chat.chattingserver.common.util.ThreadLocalLogTrace;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
@AllArgsConstructor
public class LoggerAspect {
    private final LogTrace logTrace;

    @Pointcut("execution(* com.chat.chattingserver.controller.*.*(..))")
    private void controllerPointCut() {}

    @Pointcut("execution(* com.chat.chattingserver.service.*.*(..))")
    private void servicePointCut() {}

    @Pointcut("execution(* com.chat.chattingserver.repository.*.*(..))")
    private void repoitoryointCut() {}

    @Around("controllerPointCut() || servicePointCut() || repoitoryointCut()")
    public Object logger(ProceedingJoinPoint joint) throws Throwable {
        TraceStatus status = null;
        try {
            String message = joint.getSignature().toShortString();
            status = logTrace.begin(message);
            var result = joint.proceed();
            logTrace.end(status);
            return result;
        } catch (Exception e)  {
            logTrace.exception(status, e);
            throw  e;
        }
    }
}
