package com.zyiot.commonservice.aspects;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author Administrator
 * 拦截 flowStart、flowComplete 两种开头的 方法
 * 
 * flowStart  参数为业务对象+userId+processDefinitionId 、 需要返回id
 * flowComplete 参数为业务对象+taksId 
 *
 */
@Aspect
@Component
@Order(1)
public class FlowAspect {
   /**
    * 存储flowComplete 的 taskId
    */
	private ThreadLocal<String> taskId =new ThreadLocal<String>();
	/**
	 * 任务开启人
	 */
	private ThreadLocal<String> userId =new ThreadLocal<String>();
	/**
	 * 流程定义id_
	 */
	private ThreadLocal<String> processDefinitionId =new ThreadLocal<String>();
	
	/**
	 * 存储 节点号 、 判断处理1：flowStart、2：flowComplete
	 */
	private ThreadLocal<Integer> treeNum=new ThreadLocal<Integer>();
	@Autowired
	private IdentityService   identityService;
	@Autowired
	private FormService  formService  ;
	@Autowired
	private TaskService  taskService;
	private static Logger log=LoggerFactory.getLogger(FlowAspect.class);
	
	/**
	 * 定义切点
	 */
	@Pointcut("execution(public * com.zyiot.commonservice.service.impl..flowStart*(..))")
	private void flowStartAspect(){
		
	}
	@Pointcut("execution(public * com.zyiot.commonservice.service.impl..flowComplete*(..))")
	private void flowCompleteAspect(){
		
	}
	 //方法执行前
	  @Before(value = "flowStartAspect()")
	  public void methodBefore(JoinPoint joinPoint){
		  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodBefore>>>>>>>>>>>>>>>>>>>>>>>>");
	  // String methodName=joinPoint.getSignature().getName();
	 //  if(methodName.startsWith("flowStart")){
		   treeNum.set(1);
	   	   userId.set((String) joinPoint.getArgs()[1]);
	   	   processDefinitionId.set((String) joinPoint.getArgs()[2]);
	//   	   }	
//	   else if(methodName.startsWith("flowComplete"))
//		   {
//		   treeNum.set(2);
//		   taskId.set((String) joinPoint.getArgs()[1]);
//		   }
//	   else{
//		   return ;
//	   }
	  }
	  
	  //在方法执行后
	  @AfterReturning(returning = "o",pointcut = "flowStartAspect()")
	  @Transactional
	  public void methodAfterReturing(Object o ){
		  //由于logAspect导致该事件执行了2次
		//  Integer num =  treeNum.get();
			
		
		 // if(num==null) return ;
		 // else if (num==1) {//flowStart
			  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodAfterReturing>>>>>>>>>>>>>>>>>>>>>>>>");
				identityService.setAuthenticatedUserId(userId.get());
				Map<String, String> properties = new HashMap<String, String>();
				properties.put("id",(String) o);
				formService.submitStartFormData(processDefinitionId.get(), properties);
//		  }else {//flowComplete
//			  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodAfterReturing>>>>>>>>>>>>>>>>>>>>>>>>");
//			  Task task= taskService.createTaskQuery().taskId(taskId.get()).singleResult();
//				if(task==null){
//					throw new RuntimeException("任务已被处理");
//				}
//				Map<String,String> formValues=new HashMap<String, String>();
//				formService.submitTaskFormData(taskId.get(), formValues);
//		  }
		  
	  }
	  
	  
	  
	  //方法执行前
	  @Before(value = "flowCompleteAspect()")
	  public void methodBefore1(JoinPoint joinPoint){
		  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodBefore1>>>>>>>>>>>>>>>>>>>>>>>>");
	  // String methodName=joinPoint.getSignature().getName();
	 //  if(methodName.startsWith("flowStart")){
//		   treeNum.set(1);
//	   	   userId.set((String) joinPoint.getArgs()[1]);
//	   	   processDefinitionId.set((String) joinPoint.getArgs()[2]);
//	   	   }	
//	   else if(methodName.startsWith("flowComplete"))
//		   {
		   treeNum.set(2);
		   taskId.set((String) joinPoint.getArgs()[1]);
//		   }
//	   else{
//		   return ;
//	   }
	  }
	  
	  //在方法执行后
	  @AfterReturning(returning = "o",pointcut = "flowCompleteAspect()")
	  @Transactional
	  public void methodAfterReturing1(Object o ){
		  //由于logAspect导致该事件执行了2次
		//  Integer num =  treeNum.get();
			
		
		 // if(num==null) return ;
		 // else if (num==1) {//flowStart
//			  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodAfterReturing>>>>>>>>>>>>>>>>>>>>>>>>");
//				identityService.setAuthenticatedUserId(userId.get());
//				Map<String, String> properties = new HashMap<String, String>();
//				properties.put("id",(String) o);
//				formService.submitStartFormData(processDefinitionId.get(), properties);
//		  }else {//flowComplete
			  log.info("<<<<<<<<<<<<<<<<<<<<<FlowAspect.methodAfterReturing1>>>>>>>>>>>>>>>>>>>>>>>>");
			  Task task= taskService.createTaskQuery().taskId(taskId.get()).singleResult();
				if(task==null){
					throw new RuntimeException("任务已被处理");
				}
				Map<String,String> formValues=new HashMap<String, String>();
				formService.submitTaskFormData(taskId.get(), formValues);
		  }
		  
	  }
	  
	  
	
