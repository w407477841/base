package com.zyiot.commonservice.aspects;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.druid.support.json.JSONUtils;
import com.zyiot.commonservice.entity.Log;
import com.zyiot.commonservice.service.ILogService;

@Aspect
@Component
public class LogAspect {

	ThreadLocal<Log> currLog =new ThreadLocal<Log>();
	ThreadLocal<Long> currTime =new ThreadLocal<Long>();
	@Autowired
	private ILogService   logService;
	@Value(value="${log.level}") //1所有   2,只有add+deleted+update开头
	private String  level;
	@Value(value="${tokenHead}")
	private String tokenHead;
	
	
	
private static Logger log=LoggerFactory.getLogger(LogAspect.class);
	  @Pointcut("execution(public * com.zyiot.commonservice.controller..*(..))")
	  private void controllerAspect(){
		  
	  }
	
	  //请求method前打印内容
	  @Before(value = "controllerAspect()")
	  public void methodBefore(JoinPoint joinPoint){
	   ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
	   HttpServletRequest request = requestAttributes.getRequest();
	   currTime.set(System.currentTimeMillis());
	 //  System.out.println(joinPoint.getSignature().getName());
	   String methodName=joinPoint.getSignature().getName();
	   
	   if(level.equals("2")){
		   if(!methodName.startsWith("add")&&!methodName.startsWith("deleted")&&!methodName.startsWith("update")){
			   currLog.set(null); 
			   return;
		   }   
	   }
	   String head= request.getHeader("Authorization");
	   String token =null;
	   if(head!=null&&!head.equals("")){
		   token=head.replace(tokenHead+" ", "");
	   }else{
		   token =request.getParameter("access_token");
	   }
	  
	//   Map<String , Object> map= JSONUtils.parse(Base64Decoder.decode(token.split(".")[1].getBytes(), pos, length)); 
	   Map<String , Object> map= (Map<String, Object>) JSONUtils.parse(new String(Base64.decodeBase64(token.split("\\.")[1])));
	   String user= (String) map.get("sub");
	   
	   Log l =new Log();
	   l.setUrl(request.getMethod()+" "+request.getRequestURL().toString());
	   l.setMethod(joinPoint.getSignature().toString());
	   l.setOpeTime(new Date());
	   l.setParam(Arrays.toString(joinPoint.getArgs()));
	   l.setOperator(user);
	   currLog.set(l);
	   
	  }
	  //在方法执行完结后打印返回内容
	  @AfterReturning(returning = "o",pointcut = "controllerAspect()")
	  public void methodAfterReturing(Object o ){
		 Log l=  currLog.get();
		 if(l==null) return;
		 l.setResult(o.toString());
		 l.setExpTime(System.currentTimeMillis()-currTime.get()); 
		 logService.insert(l);
	   log.info("--------------返回内容----------------");
	   log.info("Response内容:"+o.toString());
	   log.info("--------------返回内容----------------");
	  }
	  
	  @AfterThrowing(pointcut="controllerAspect()",throwing="e")
	  public void methodAfterThrowing(JoinPoint point, Throwable e){
		  Log l=  currLog.get();
			 if(l==null) return;
			 l.setResult("{message:"+e.getMessage()+"}");
			 l.setExpTime(System.currentTimeMillis()-currTime.get()); 
			 logService.insert(l);
		   log.info("--------------返回内容----------------");
		   log.info("Response内容:"+l.getResult());
		   log.info("--------------返回内容----------------");
	  }
	  
}
