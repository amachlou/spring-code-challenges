package com.amachlou.springchallenges;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.xml.JobParserJobFactoryBean;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {
    Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    @Around("execution(* com.amachlou.springchallenges.CateringJobController.*ById(Long)) && args(id)")
    public Object befoireReq(ProceedingJoinPoint proceedingJoinPoint, Long id) throws Throwable {
        logger.info("Invoked Method: {}", proceedingJoinPoint.getSignature().getName());
        logger.info("CateringJob id: {}", id);
        Object result = proceedingJoinPoint.proceed();
        logger.info("Response id: {}", result);
        return result;
    }

}
