package com.zyiot.commonservice.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.annotations.Push;
import com.zyiot.commonservice.push.service.IPushService;

@Aspect
@Component
public class PushAspect {
	@Autowired
	IPushService  pushService;
	
	private static final Logger log=LoggerFactory.getLogger(DataWarningAspect.class);
    @Around(value = "execution(* com..*.*(..))&&@annotation(push)")
    public Object aroundMethod(ProceedingJoinPoint pjd, Push push) {
        Object result = null;
       
         try {
            result = pjd.proceed();
            pushService.push(push.message());
        } catch (Throwable e) {
        	
        }

         return result;
    }

	
}
