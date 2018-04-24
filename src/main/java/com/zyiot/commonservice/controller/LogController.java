package com.zyiot.commonservice.controller;

import java.util.ArrayList;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.baomidou.mybatisplus.plugins.Page;
import com.zyiot.commonservice.entity.Log;
import com.zyiot.commonservice.service.ILogService;
/**
 * @author 王一飞
 * @since 2017-12-14 15:16:13
 */

@Controller
@RequestMapping("/log")
public class  LogController {
	 @Autowired
	private ILogService  logService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('log_add')")
	@ApiOperation(value="创建日志", notes="根据log创建日志,需要权限log_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "log", value = "日志", required = true, dataType = "Log",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Log> add(@RequestBody() Log log){
		try {
			if (logService.insert(log)) {
				return new ResponseEntity<Log>(log, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Log>( log,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('log_deleted')")
	@ApiOperation(value="删除日志", notes="根据id删除日志，需要权限log_deleted')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public  ResponseEntity<String> deleted(@PathVariable(value="id")Long id){
		try {
			if(logService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Log
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('log_update')")
	@ApiOperation(value="修改日志", notes="根据log修改日志，需要权限log_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "log", value = "日志", required = true, dataType = "Log",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Log> update(@RequestBody Log log){
		
		try {
			if(logService.updateById(log)){
				return new  ResponseEntity<Log>(log,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Log>(log,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('log_info')")
	@ApiOperation(value="查询日志", notes="根据id查询日志，需要权限log_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Log.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Log> findByid(@PathVariable("id")Long id){
		
		
		     Log log=null;
			try {
				log = logService.selectById(id);
				 
				 if(log !=null) {
					 return new  ResponseEntity<Log>(log,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Log>(new Log(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Log>(new Log(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Log
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('log_page')")
	@ApiOperation(value="分页查询日志", notes="根据log分页日志，需要权限log_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "log", value = "日志", required = true, dataType = "Log",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Log>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Log log){
		Page<Log> page=null;
		try {
			page=new Page<Log>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= logService.findLogByPage(page, log);
			
			
			
			return new ResponseEntity<Page<Log>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Log>>(new Page<Log>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('log_all')")
		@ApiOperation(value="查询日志", notes="根据log查询日志")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "method", value = "方法", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "url", value = "请求地址", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "param", value = "参数", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "result", value = "结果", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "opeTime", value = "操作时间", required = false, dataType = "Date",paramType="query")   
												,@ApiImplicitParam(name = "expTime", value = "耗时", required = false, dataType = "Long",paramType="query")   
												,@ApiImplicitParam(name = "operator", value = "操作人", required = false, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<Log>> findRoleAll(
				 @RequestParam(value="method",required=false)String method
												 ,@RequestParam(value="url",required=false)String url
			  									 ,@RequestParam(value="param",required=false)String param
			  									 ,@RequestParam(value="result",required=false)String result
			  									 ,@RequestParam(value="opeTime",required=false)Date opeTime
			  									 ,@RequestParam(value="expTime",required=false)Long expTime
			  									 ,@RequestParam(value="operator",required=false)String operator
			  				){
			
		Log log =new Log();
						log.setMethod(method);
						log.setUrl(url);
						log.setParam(param);
						log.setResult(result);
						log.setOpeTime(opeTime);
						log.setExpTime(expTime);
						log.setOperator(operator);
					List<Log> logs= null;
	
			 logs=logService.findLogAll(log);
			 return new ResponseEntity<List<Log>>(logs, HttpStatus.OK);
	}
	
	
	
	
}
