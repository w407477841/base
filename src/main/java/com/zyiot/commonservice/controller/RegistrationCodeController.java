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
import com.zyiot.commonservice.entity.RegistrationCode;
import com.zyiot.commonservice.service.IRegistrationCodeService;
/**
 * @author 王一飞
 * @since 2018-03-29 14:33:27
 */

@Controller
@RequestMapping("/registrationCode")
public class  RegistrationCodeController {
	 @Autowired
	private IRegistrationCodeService  registrationCodeService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('registrationCode_add')")
	@ApiOperation(value="创建注册码", notes="根据registrationCode创建注册码管理,需要权限registrationCode_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name="factoryId",value="工厂ID",required=true,dataType="Long",paramType="query"),
	@ApiImplicitParam(name="count",value="生成数量",required=true,dataType="Integer",paramType="query"),
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Object> add(@RequestParam(value="factoryId",required=true)Long factoryId,@RequestParam(value="count",required=true)Integer count){
			
			List<RegistrationCode> list= registrationCodeService.insert(factoryId,count);
				return new ResponseEntity<Object>(list, HttpStatus.OK);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('registrationCode_deleted')")
	@ApiOperation(value="删除注册码管理", notes="根据id删除注册码管理，需要权限registrationCode_deleted')")
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
			if(registrationCodeService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param RegistrationCode
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('registrationCode_update')")
	@ApiOperation(value="修改注册码管理", notes="根据registrationCode修改注册码管理，需要权限registrationCode_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "registrationCode", value = "注册码管理", required = true, dataType = "RegistrationCode",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< RegistrationCode> update(@RequestBody RegistrationCode registrationCode){
		
		try {
			if(registrationCodeService.updateById(registrationCode)){
				return new  ResponseEntity<RegistrationCode>(registrationCode,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< RegistrationCode>(registrationCode,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('registrationCode_info')")
	@ApiOperation(value="查询注册码管理", notes="根据id查询注册码管理，需要权限registrationCode_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=RegistrationCode.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<RegistrationCode> findByid(@PathVariable("id")Long id){
		
		
		     RegistrationCode registrationCode=null;
			try {
				registrationCode = registrationCodeService.selectById(id);
				 
				 if(registrationCode !=null) {
					 return new  ResponseEntity<RegistrationCode>(registrationCode,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<RegistrationCode>(new RegistrationCode(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<RegistrationCode>(new RegistrationCode(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param RegistrationCode
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('registrationCode_page')")
	@ApiOperation(value="分页查询注册码管理", notes="根据registrationCode分页注册码管理，需要权限registrationCode_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "registrationCode", value = "注册码管理", required = true, dataType = "RegistrationCode",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<RegistrationCode>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody RegistrationCode registrationCode){
		Page<RegistrationCode> page=null;
		try {
			page=new Page<RegistrationCode>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= registrationCodeService.findRegistrationCodeByPage(page, registrationCode);
			
			
			
			return new ResponseEntity<Page<RegistrationCode>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<RegistrationCode>>(new Page<RegistrationCode>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param RegistrationCode
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('registrationCode_page')")
	@ApiOperation(value="分页查询注册码管理", notes="根据key分页注册码管理，需要权限registrationCode_info")
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
	public ResponseEntity<Page<RegistrationCode>> fingUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam (value="key",required=false)String key){
		Page<RegistrationCode> page=null;
		try {
			page=new Page<RegistrationCode>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= registrationCodeService.findOrRegistrationCodeByPage(page, key);
			
			
			
			return new ResponseEntity<Page<RegistrationCode>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<RegistrationCode>>(new Page<RegistrationCode>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('registrationCode_all')")
		@ApiOperation(value="查询注册码管理", notes="根据registrationCode查询注册码管理")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "code", value = "注册码", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "factoryId", value = "工厂", required = false, dataType = "Long",paramType="query")   
												,@ApiImplicitParam(name = "status", value = "状态", required = false, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<RegistrationCode>> findRoleAll(
				 @RequestParam(value="code",required=false)String code
												 ,@RequestParam(value="factoryId",required=false)Long factoryId
			  									 ,@RequestParam(value="status",required=false)String status
			  				){
		RegistrationCode registrationCode =new RegistrationCode();
						registrationCode.setCode(code);
						registrationCode.setFactoryId(factoryId);
						registrationCode.setStatus(status);
					List<RegistrationCode> registrationCodes= null;
		 try {
			 registrationCodes=registrationCodeService.findRegistrationCodeAll(registrationCode);
			 return new ResponseEntity<List<RegistrationCode>>(registrationCodes, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<RegistrationCode>>(new ArrayList<RegistrationCode>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
