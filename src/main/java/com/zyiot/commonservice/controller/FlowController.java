package com.zyiot.commonservice.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.FormType;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.task.Task;
import org.activiti.image.ProcessDiagramGenerator;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.plugins.Page;
import com.zyiot.commonservice.entity.ShangjiInfo;
import com.zyiot.commonservice.pojo.FlowForm;
import com.zyiot.commonservice.pojo.TaskRepresentation;
import com.zyiot.commonservice.service.impl.FlowServiceImpl;
@RestController
@RequestMapping("flow")
public class FlowController {
	@Autowired
	private FlowServiceImpl  flowServiceImpl;
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param ShangjiInfo
	 * @return
	 */
	@PostMapping("/todotasks/page/")
	@PreAuthorize(value="hasRole('flow_todotasks_page')")
	@ApiOperation(value="分页查询待办任务", notes="需要权限todotasks_page")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "shangjiInfo", value = "商机信息", required = true, dataType = "ShangjiInfo",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<TaskRepresentation>> fingTodotasksByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody TaskRepresentation task){
		Page<TaskRepresentation> page=null;
		try {
			page =new Page<TaskRepresentation>(pageNum, pageSize);
			
			page =flowServiceImpl.todoTasks(task.getAssignee(), page);
			return new ResponseEntity<Page<TaskRepresentation>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<TaskRepresentation>>(new Page<TaskRepresentation>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 流程图
	 * @param taskId
	 * @param response
	 */
	@RequestMapping(method=RequestMethod.GET,value="processImage")
	@PreAuthorize(value="hasRole('processImage')")
	public void processImage(String taskId,HttpServletResponse response){
		
        try {  
        	InputStream inputStream= flowServiceImpl.processImage(taskId);
	        int size = inputStream.available();  
	        byte data[] = new byte[size];  
	        inputStream.read(data);  
	        response.setContentType("image/png"); // 设置返回的文件类型  
	        OutputStream os = response.getOutputStream();  
	        os.write(data);  
	        os.flush();  
	        os.close();  
	    } catch (IOException e) {  
	    }  
		
	}

	/**
	 * 签收
	 * @param param
	 * @return
	 */
	@PostMapping(value="claim")
	@PreAuthorize(value="hasRole('flow_claim')")
	@ApiOperation(value="签收", notes="需要权限flow_claim")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "shangjiInfo", value = "参数", required = true, dataType = "Map",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=400,message="签收失败",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<String> claim( @RequestBody Map<String, String> param){
		Map<String, Object> result=new HashMap<String, Object>();
		result.put("msg", "成功");
	 try {
		 flowServiceImpl.addClaim(param.get("taskId"), param.get("userId"));
		return new ResponseEntity<String>("", HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		 return new ResponseEntity<String>("", HttpStatus.BAD_REQUEST);
	}

	// return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@GetMapping(value="readform")
	@PreAuthorize(value="hasRole('flow_readform')")
	@ApiOperation(value="读取参数", notes="需要权限flow_readform")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "taskId", value = "任务编号", required = true, dataType = "String",paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=400,message="获取失败",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<FlowForm> readForm( @RequestParam("taskId")String taskId){
		FlowForm flowForm =null;
		try {
			flowForm = flowServiceImpl.readForms(taskId);
		 	return new ResponseEntity<FlowForm>(flowForm, HttpStatus.OK);
	} catch (Exception e) {
		e.printStackTrace();
		 return new ResponseEntity<FlowForm>(flowForm, HttpStatus.BAD_REQUEST);
	}
		
	}
	
	
}
