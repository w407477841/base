package com.zyiot.commonservice.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.baomidou.mybatisplus.plugins.Page;
import com.zyiot.commonservice.mapper.FlowMapper;
import com.zyiot.commonservice.pojo.FlowForm;
import com.zyiot.commonservice.pojo.TaskRepresentation;

@Service("flowService")
public class FlowServiceImpl {
	@Autowired
	private FlowMapper flowMapper;
	@Autowired
	private  TaskService taskService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private HistoryService historyService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private FormService  formService;
	
	
	@Autowired
	private ProcessEngineConfiguration processEngineConfiguration;
	/**
	 * 流程审批节点调用的方法
	 * @param column
	 * @param execution
	 * @return
	 */
	public boolean approve(String column,DelegateExecution execution){
		Map<String, Object> map= execution.getVariables();
		String id=   (String) map.get("id");
	    String tableName= (String) map.get("table");
	    
	    Map<String, Object> obj= flowMapper.selectObj(tableName, Integer.parseInt(id));
	    
	     Integer  approve=  (Integer) obj.get(column);
	    if(approve == 1) {// 不通过
	    	return true;
	    }
		return false;
	}
	/**
	 * 根据部门查询人
	 * @param bm
	 * @param execution
	 * @return
	 */
	public List<String> findUsers(String bm,DelegateExecution execution){
		//根据部门查人
		List<String> users=new ArrayList<String>();
		if(bm.equals("商务部")){
			users.add("jianghua");
		}
		if(bm.equals("销售部")){
			users.add("dongwei");
		}
		
		return users;
	}
	/**
	 * 根据用户 分页查询该用户下所有待办事项
	 * @param userId
	 * @param page
	 * @return
	 */
	public Page<TaskRepresentation> todoTasks(String userId,Page<TaskRepresentation> page){
		List<Task> 	tasksAssigne= taskService.createTaskQuery().taskAssignee(userId).list();
		//如果使用activiti提供用户和组 ，会查出 CandidateUser + CandidateGroup的数据
		List<Task> 	tasksCandidateUser= taskService.createTaskQuery().taskCandidateUser(userId).list();
	//此处不需要,控制该项目所有流程不使用组	List<Task>  tasksCandidateGroup = taskService.createTaskQuery().taskCandidateGroupIn(compService.selectNameByUserId(userId)).list(); 
		List<Task> tasksAll= new ArrayList<Task>();
		tasksAll.addAll(tasksAssigne);
		tasksAll.addAll(tasksCandidateUser);
	//	tasksAll.addAll(tasksCandidateGroup);
		
		page.getCurrent();
		page.getSize();
		int from=(page.getCurrent()-1)*page.getSize();
		int to= from + page.getSize();
		if(to>tasksAll.size()){
			to=tasksAll.size();
		}
		List<Task> pages=tasksAll.subList(from, to);
		
		List<TaskRepresentation> dtos = new ArrayList<TaskRepresentation>();
		for(Task task : pages){
		 dtos.add(new TaskRepresentation(task.getId(), task.getName(),task.getProcessInstanceId(),task.getAssignee(),new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(task.getCreateTime())));
		}
		page.setRecords(dtos);
		page.setTotal(tasksAll.size());
		return page;
	}
	
	public InputStream processImage(String taskId){
		Map<String, Object> result= new HashMap<String, Object>();
		//String processInstanceId=(String) param.get("param");
		Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		
		BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());  
  
        // 经过的节点  
        List<String> activeActivityIds = new ArrayList<>();  
        List<String> finishedActiveActivityIds = new ArrayList<>();  
     // 已执行完的任务节点  
        List<HistoricActivityInstance> finishedInstances = historyService.createHistoricActivityInstanceQuery().processInstanceId(task.getProcessInstanceId()).finished().list();  
        for (HistoricActivityInstance hai : finishedInstances) {  
            finishedActiveActivityIds.add(hai.getActivityId());  
        }  
  
        // 已完成的节点+当前节点  
        activeActivityIds.addAll(finishedActiveActivityIds);  
        activeActivityIds.addAll(runtimeService.getActiveActivityIds(task.getProcessInstanceId()));  
  
        // 经过的流  
        ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService.getProcessDefinition(task.getProcessDefinitionId());  
        List<String> highLightedFlows = new ArrayList<>();  
        getHighLightedFlows(processDefinitionEntity.getActivities(), highLightedFlows, activeActivityIds);  
  
        ProcessDiagramGenerator pdg = processEngineConfiguration.getProcessDiagramGenerator();  
        InputStream inputStream = pdg.generateDiagram(bpmnModel, "PNG", finishedActiveActivityIds, highLightedFlows,  
        		"WenQuanYi Micro Hei",  //修改字体类型 默认Arial，使用中文乱码
                "WenQuanYi Micro Hei",  //修改字体类型 默认Arial，使用中文乱码
                "WenQuanYi Micro Hei",//修改字体类型 默认Arial，使用中文乱码
                processEngineConfiguration.getProcessEngineConfiguration().getProcessEngineConfiguration().getClassLoader(), 1.0);  
		//result.put("data", taskId);
        
        return inputStream;
		
	}
	private void getHighLightedFlows(List<ActivityImpl> activityList, List<String> highLightedFlows, List<String> historicActivityInstanceList) {  
	       for (ActivityImpl activity : activityList) {  
	           if (activity.getProperty("type").equals("subProcess")) {  
	               // get flows for the subProcess  
	               getHighLightedFlows(activity.getActivities(), highLightedFlows, historicActivityInstanceList);  
	           }  
	  
	           if (historicActivityInstanceList.contains(activity.getId())) {  
	               List<PvmTransition> pvmTransitionList = activity.getOutgoingTransitions();  
	               for (PvmTransition pvmTransition : pvmTransitionList) {  
	                   String destinationFlowId = pvmTransition.getDestination().getId();  
	                   if (historicActivityInstanceList.contains(destinationFlowId)) {  
	                       highLightedFlows.add(pvmTransition.getId());  
	                   }  
	               }  
	           }  
	       }  
	   }
	/**
	 * 签收
	 * @param taskId
	 * @param userId
	 */
	public void addClaim(String taskId, String userId) {
		
		taskService.claim(taskId, userId);
		
	}  
	/**
	 * 获取 任务变量
	 * @param taskId
	 * @return
	 */
	public FlowForm readForms(String taskId){
		//TaskFormData taskFormData= formService.getTaskFormData(taskId);
		 Task task=taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String, Object>   maps=   runtimeService.getVariables(task.getExecutionId());
		FlowForm flowForm=new FlowForm();
		flowForm.setId((String) maps.get("id"));
		flowForm.setTable((String) maps.get("table"));
		flowForm.setUrl((String) maps.get("url"));
		return flowForm;
	}
	
	public void changeUrl(String url,DelegateExecution execution){
		execution.setVariable("url", url);
	}
	
}
