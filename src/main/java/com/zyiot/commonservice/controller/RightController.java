package com.zyiot.commonservice.controller;

import java.util.ArrayList;

import io.swagger.annotations.Api;
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
import com.zyiot.commonservice.entity.Right;
import com.zyiot.commonservice.service.IRightService;
/**
 * @author 王一飞
 * @since 2017-12-13 13:46:24
 */

@Controller
@RequestMapping("/right")

public class  RightController {
	 @Autowired
	private IRightService  rightService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('right_add')")
	@ApiOperation(value="创建权限", notes="根据right创建权限,需要权限right_add")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "right", value = "权限", required = true, dataType = "Right",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Right> add(@RequestBody() Right right){
		try {
			if (rightService.insert(right)) {
				return new ResponseEntity<Right>(right, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Right>( right,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('right_deleted')")
	@ApiOperation(value="删除权限", notes="根据id删除权限，需要权限right_deleted')")
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
			if(rightService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Right
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('right_update')")
	@ApiOperation(value="修改权限", notes="根据right修改权限，需要权限right_update')")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "right", value = "权限", required = true, dataType = "Right",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Right> update(@RequestBody Right right){
		
		try {
			if(rightService.updateById(right)){
				return new  ResponseEntity<Right>(right,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Right>(right,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('right_info')")
	@ApiOperation(value="查询权限", notes="根据id查询权限，需要权限right_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Right.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Right> findByid(@PathVariable("id")Long id){
		
		
		     Right right=null;
			try {
				right = rightService.selectById(id);
				 
				 if(right !=null) {
					 return new  ResponseEntity<Right>(right,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Right>(new Right(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Right>(new Right(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Right
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('right_page')")
	@ApiOperation(value="分页查询权限", notes="根据right分页权限，需要权限right_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "right", value = "权限", required = true, dataType = "Right",paramType="body")
	})
	@ApiResponses(value={
			
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Right>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Right right){
		Page<Right> page=null;
		try {
			page=new Page<Right>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= rightService.findRightByPage(page, right);
			
			
			
			return new ResponseEntity<Page<Right>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Right>>(new Page<Right>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('right_all')")
		@ApiOperation(value="查询权限", notes="根据right查询权限")
		
			@ApiImplicitParams(value={
					@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
						 @ApiImplicitParam(name = "rightName", value = "权限名", required = false, dataType = "String",paramType="query")
												 ,@ApiImplicitParam(name = "moduleId", value = "模块号", required = false, dataType = "Long",paramType="query")   
												 ,@ApiImplicitParam(name = "availableDevices", value = "可用设备三位 1表示可用0表示不可用 web，安卓，ios；111表示三个都可用", required = false, dataType = "String",paramType="query")   
												 ,@ApiImplicitParam(name = "url", value = "资源地址", required = false, dataType = "String",paramType="query")   
												 ,@ApiImplicitParam(name = "rightTxt", value = "返回客户端操作的名称", required = false, dataType = "String",paramType="query")   
							})
		
	
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	
	
	
	public ResponseEntity<List<Right>> findRoleAll(
				 @RequestParam(value="rightName",required=false)String rightName
												 ,@RequestParam(value="moduleId",required=false)Long moduleId
			  									 ,@RequestParam(value="availableDevices",required=false)String availableDevices
			  									 ,@RequestParam(value="url",required=false)String url
			  									 ,@RequestParam(value="rightTxt",required=false)String rightTxt
			  				
	
	
	){
		Right right =new Right();
						right.setRightName(rightName);
						right.setModuleId(moduleId);
						right.setAvailableDevices(availableDevices);
						right.setUrl(url);
						right.setRightTxt(rightTxt);
					List<Right> rights= null;
		 try {
			 rights=rightService.findRightAll(right);
			 return new ResponseEntity<List<Right>>(rights, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Right>>(new ArrayList<Right>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
		@GetMapping("/{moduleId}/user/{username}/")
		@PreAuthorize(value="hasRole('right_all')")
		@ApiOperation(value="查询权限", notes="根据模块号和用户名查询查询权限")
		
			@ApiImplicitParams(value={
					@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
						 @ApiImplicitParam(name = "moduleId", value = "模块号", required = true, dataType = "Long",paramType="path")
						,@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType="path")   
										   
							})
		
	
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	
	
	
	public ResponseEntity<List<Right>> findByModuleAndUsername(
				 @RequestParam(value="rightName",required=false)String rightName
												 ,@PathVariable(value="moduleId",required=true)Long moduleId
			  									 ,@PathVariable(value="username",required=true)String username
			  									 
			  				
	
	
	){
					List<Right> rights= null;
		 try {
			 rights=rightService.findByModuleAndUsername(moduleId,username);
			 return new ResponseEntity<List<Right>>(rights, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Right>>(new ArrayList<Right>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
