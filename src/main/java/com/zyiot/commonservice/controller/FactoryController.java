package com.zyiot.commonservice.controller;

import java.util.ArrayList;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.zyiot.commonservice.entity.Factory;
import com.zyiot.commonservice.service.IFactoryService;
/**
 * @author 王一飞
 * @since 2018-03-28 13:53:47
 */

@Controller
@RequestMapping("/factory")
public class  FactoryController {
	 @Autowired
	private IFactoryService  factoryService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('factory_add')")
	@ApiOperation(value="创建工厂管理", notes="根据factory创建工厂管理,需要权限factory_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "factory", value = "工厂管理", required = true, dataType = "Factory",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Factory> add(@RequestBody() Factory factory){
		try {
			if (factoryService.insert(factory)) {
				return new ResponseEntity<Factory>(factory, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Factory>( factory,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('factory_deleted')")
	@ApiOperation(value="删除工厂管理", notes="根据id删除工厂管理，需要权限factory_deleted')")
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
			if(factoryService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Factory
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('factory_update')")
	@ApiOperation(value="修改工厂管理", notes="根据factory修改工厂管理，需要权限factory_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "factory", value = "工厂管理", required = true, dataType = "Factory",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Factory> update(@RequestBody Factory factory,HttpServletRequest request){
		@SuppressWarnings("unused")
		Map<String, String[]> mpMap=request.getParameterMap();
		try {
			if(factoryService.updateById(factory)){
				return new  ResponseEntity<Factory>(factory,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Factory>(factory,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('factory_info')")
	@ApiOperation(value="查询工厂管理", notes="根据id查询工厂管理，需要权限factory_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Factory.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Factory> findByid(@PathVariable("id")Long id){
		
		
		     Factory factory=null;
			try {
				factory = factoryService.selectById(id);
				 
				 if(factory !=null) {
					 return new  ResponseEntity<Factory>(factory,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Factory>(new Factory(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Factory>(new Factory(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Factory
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('factory_page')")
	@ApiOperation(value="分页查询工厂管理", notes="根据factory分页工厂管理，需要权限factory_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "factory", value = "工厂管理", required = true, dataType = "Factory",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Factory>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Factory factory){
		Page<Factory> page=null;
		try {
			page=new Page<Factory>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= factoryService.findFactoryByPage(page, factory);
			
			
			
			return new ResponseEntity<Page<Factory>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Factory>>(new Page<Factory>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Factory
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('factory_page')")
	@ApiOperation(value="分页查询工厂管理", notes="根据key分页工厂管理，需要权限factory_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "key", value = "关键词", required = false, dataType = "String",paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Factory>> fingUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam (value="key",required=false)String key){
		Page<Factory> page=null;
		try {
			page=new Page<Factory>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= factoryService.findOrFactoryByPage(page, key);
			
			
			
			return new ResponseEntity<Page<Factory>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Factory>>(new Page<Factory>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('factory_all')")
		@ApiOperation(value="查询工厂管理", notes="根据factory查询工厂管理")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "bh", value = "编号", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "name", value = "名称", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "addr", value = "地址", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "phone", value = "联系方式", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "owner", value = "负责人", required = false, dataType = "Long",paramType="query")   
												,@ApiImplicitParam(name = "startDay", value = "开始时间", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "endDay", value = "结束时间", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "max", value = "最大用户数", required = false, dataType = "Integer",paramType="query")   
												,@ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<Factory>> findRoleAll(
				 @RequestParam(value="bh",required=false)String bh
												 ,@RequestParam(value="name",required=false)String name
			  									 ,@RequestParam(value="addr",required=false)String addr
			  									 ,@RequestParam(value="phone",required=false)String phone
			  									 ,@RequestParam(value="owner",required=false)Long owner
			  									 ,@RequestParam(value="startDay",required=false)String startDay
			  									 ,@RequestParam(value="endDay",required=false)String endDay
			  									 ,@RequestParam(value="max",required=false)Integer max
			  									 ,@RequestParam(value="status",required=false)String status
			  				){
		Factory factory =new Factory();
						factory.setBh(bh);
						factory.setName(name);
						factory.setAddr(addr);
						factory.setPhone(phone);
						factory.setOwner(owner);
						factory.setStartDay(startDay);
						factory.setEndDay(endDay);
						factory.setMax(max);
						factory.setStatus(status);
					List<Factory> factorys= null;
		 try {
			 factorys=factoryService.findFactoryAll(factory);
			 return new ResponseEntity<List<Factory>>(factorys, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Factory>>(new ArrayList<Factory>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
