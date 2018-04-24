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
import com.zyiot.commonservice.entity.RoleRight;
import com.zyiot.commonservice.service.IRoleRightService;
/**
 * @author 王一飞
 * @since 2017-12-13 13:36:01
 */

@Controller
@RequestMapping("/roleRight")
public class  RoleRightController {
	 @Autowired
	private IRoleRightService  roleRightService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('roleRight_add')")
	@ApiOperation(value="创建角色权限关系", notes="根据roleRight创建角色权限关系,需要权限roleRight_add")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "roleRight", value = "角色权限关系", required = true, dataType = "RoleRight",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< RoleRight> add(@RequestBody() RoleRight roleRight){
		try {
			if (roleRightService.insert(roleRight)) {
				return new ResponseEntity<RoleRight>(roleRight, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< RoleRight>( roleRight,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('roleRight_deleted')")
	@ApiOperation(value="删除角色权限关系", notes="根据id删除角色权限关系，需要权限roleRight_deleted')")
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
			if(roleRightService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param RoleRight
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('roleRight_update')")
	@ApiOperation(value="修改角色权限关系", notes="根据roleRight修改角色权限关系，需要权限roleRight_update')")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "roleRight", value = "角色权限关系", required = true, dataType = "RoleRight",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< RoleRight> update(@RequestBody RoleRight roleRight){
		
		try {
			if(roleRightService.updateById(roleRight)){
				return new  ResponseEntity<RoleRight>(roleRight,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< RoleRight>(roleRight,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('roleRight_info')")
	@ApiOperation(value="查询角色权限关系", notes="根据id查询角色权限关系，需要权限roleRight_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=RoleRight.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<RoleRight> findByid(@PathVariable("id")Long id){
		
		
		     RoleRight roleRight=null;
			try {
				roleRight = roleRightService.selectById(id);
				 
				 if(roleRight !=null) {
					 return new  ResponseEntity<RoleRight>(roleRight,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<RoleRight>(new RoleRight(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<RoleRight>(new RoleRight(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param RoleRight
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('roleRight_page')")
	@ApiOperation(value="分页查询角色权限关系", notes="根据roleRight分页角色权限关系，需要权限roleRight_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "roleRight", value = "角色权限关系", required = true, dataType = "RoleRight",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<RoleRight>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody RoleRight roleRight){
		Page<RoleRight> page=null;
		try {
			page=new Page<RoleRight>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= roleRightService.findRoleRightByPage(page, roleRight);
			
			
			
			return new ResponseEntity<Page<RoleRight>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<RoleRight>>(new Page<RoleRight>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('roleRight_all')")
		@ApiOperation(value="查询角色权限关系", notes="根据roleRight查询角色权限关系")
		
			@ApiImplicitParams(value={
					@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
						 @ApiImplicitParam(name = "roleId", value = "角色编号", required = false, dataType = "Long",paramType="query")
												 ,@ApiImplicitParam(name = "rightId", value = "权限编号", required = false, dataType = "Long",paramType="query")   
							})
		
	
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	
	
	
	public ResponseEntity<List<RoleRight>> findRoleAll(
				 @RequestParam(value="roleId",required=false)Long roleId
												 ,@RequestParam(value="rightId",required=false)Long rightId
			  				
	
	
	){
		RoleRight roleRight =new RoleRight();
						roleRight.setRoleId(roleId);
						roleRight.setRightId(rightId);
					List<RoleRight> roleRights= null;
		 try {
			 roleRights=roleRightService.findRoleRightAll(roleRight);
			 return new ResponseEntity<List<RoleRight>>(roleRights, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<RoleRight>>(new ArrayList<RoleRight>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
