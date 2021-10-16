package kdt.prgrms.devrun.common.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ServiceLoggingAspect {

    @Around(
        "execution(public * kdt.prgrms.devrun.post..*Service.*(..))"
//        + " || execution(public * kdt.prgrms.devrun.user..*Service.*(..))"
        )
    public Object doLogging(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();

        Object result = null;

        try {
            log.info("Start [{}] [{}]: Parameter: {}", pjp.getTarget().getClass(), pjp.getSignature().getName(), pjp.getArgs());
            result = pjp.proceed(pjp.getArgs());
            return result;
        } finally {
            long end = System.currentTimeMillis();
            log.info("End [{}] [{}]: Return: {} ({}ms)", pjp.getTarget().getClass(), pjp.getSignature().getName(), result, end-start);

        }

    }

}
