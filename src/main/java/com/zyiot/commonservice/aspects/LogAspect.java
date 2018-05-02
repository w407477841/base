package com.zyiot.commonservice.aspects;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
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
import com.google.gson.Gson;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion.User;
import com.zyiot.commonservice.annotations.DataWarning;
import com.zyiot.commonservice.entity.Log;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
import com.zyiot.commonservice.service.ILogService;
/**
 * 
 * @author Administrator
 * 自定义日志通知	 
 *
 */
@Aspect
@Component
public class LogAspect {

	@Autowired
	private ILogService   logService;
	@Value(value="${log.level}") //1所有   2,只有add+deleted+update开头
	private String  level;
	@Value(value="${tokenHead}")
	private String tokenHead;
	@Autowired
	private IMessageQueueService  messageQueueService;
	
private static Logger log=LoggerFactory.getLogger(LogAspect.class);
	  @Pointcut("execution(public * com.zyiot.commonservice.controller..*(..))")
	  private void controllerAspect(){
		  
	  }
	
	  @Around(value="controllerAspect()")
	  public Object aroundMethod(ProceedingJoinPoint pjd) {
		  ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		   HttpServletRequest request = requestAttributes.getRequest();
		   Log log=new Log();
		   boolean isError =false;
		   Throwable currentThrowable =null;
		   Long startmillis = System.currentTimeMillis();
		   Object result=null;
		  try {
			result=pjd.proceed();
			log.setResult(result.toString());
		} catch (Throwable e) {
			log.setResult("{message:"+e.getMessage()+"}");
			isError = true;
			currentThrowable = e;
		}
		  Long endmillis = System.currentTimeMillis();
		  log.setExpTime(endmillis-startmillis);
		   String head= request.getHeader("Authorization");
		   String token =null;
		   if(head!=null&&!head.equals("")){
			   token=head.replace(tokenHead+" ", "");
		   }else{
			   token =request.getParameter("access_token");
		   }
		   //获取用户
		   String user=null;
		   if(token==null){
			   user="匿名";
		   }else{
		   Map<String , Object> map= (Map<String, Object>) JSONUtils.parse(new String(Base64.decodeBase64(token.split("\\.")[1])));
		      user= (String) map.get("sub");
		   }
		   //设置URL
		   log.setUrl(request.getMethod()+" "+request.getRequestURL().toString());
		   //设置触发方法
		   log.setMethod(pjd.getSignature().toString());
		   //设置当前时间
		   log.setOpeTime(new Date());
		   //设置日志参数
		   log.setParam(Arrays.toString(pjd.getArgs()));
		   //设置执行人
		   log.setOperator(user);
		   
		   //加入队列
		   messageQueueService.send(MQConstant.LOG_ROUTING_KEY, new Gson().toJson(log));
		   
		   if(isError){
			   throw new RuntimeException(currentThrowable.getMessage());
		   }
		   
		   return result;
	  }
	  
	  
}
