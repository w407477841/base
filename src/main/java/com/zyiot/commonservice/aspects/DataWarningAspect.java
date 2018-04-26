package com.zyiot.commonservice.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sun.tools.javac.util.List;
import com.zyiot.commonservice.annotations.DataWarning;
import com.zyiot.commonservice.entity.DataWarningInfo;


/**
 * 数据警告通知AOP
 * 对需要进行数据匹配警告的service方法使用@DataWarning()
 */
@Component
@Aspect
public class DataWarningAspect {

	private static final Logger log=LoggerFactory.getLogger(DataWarningAspect.class);
	    @Around(value = "execution(* com.zyiot.commonservice.service..*(..)) && @annotation(dataWarning)")
	    public Object aroundMethod(ProceedingJoinPoint pjd, DataWarning dataWarning) {
	        Object result = null;
	        boolean isError=false;
	        System.out.println("前置通知");
	        List<DataWarningInfo> datas=null;
	         try {
				Object [] args=   pjd.getArgs();
				 datas=(List<DataWarningInfo>) args[dataWarning.dataParamIndex()];
			} catch (Exception e1) {
				isError=true;
			} 
	       
	         try {
	            result = pjd.proceed();
	            System.out.println("后置通知");
	        } catch (Throwable e) {
	        	isError=true;
	            System.out.println("异常通知");
	        }
	         if(!isError){//增加并行处理效率
	        	 //放入队列
	         }
	        System.out.println("返回通知");
	        return result;
	    }
	
	
}
