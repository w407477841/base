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
import com.zyiot.commonservice.entity.Role;
import com.zyiot.commonservice.entity.RoleRight;
import com.zyiot.commonservice.service.IRightService;
import com.zyiot.commonservice.service.IRoleRightService;
import com.zyiot.commonservice.service.IRoleService;
/**
 * @author 王一飞
 * @since 2017-12-13 13:36:00
 */

@Controller
@RequestMapping("/role")
public class  RoleController {
	 @Autowired
	private IRoleService  roleService;
	 @Autowired
	 private IRoleRightService roleRightService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('role_add')")
	@ApiOperation(value="创建角色", notes="根据role创建角色,需要权限role_add")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Role",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Role> add(@RequestBody() Role role){
		try {
			if (roleService.insert(role)) {
				return new ResponseEntity<Role>(role, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Role>( role,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('role_deleted')")
	@ApiOperation(value="删除角色", notes="根据id删除角色，需要权限role_deleted')")
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
			if(roleService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Role
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('role_update')")
	@ApiOperation(value="修改角色", notes="根据role修改角色，需要权限role_update')")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Role",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Role> update(@RequestBody Role role){
		
		try {
			if(roleService.updateById(role)){
				return new  ResponseEntity<Role>(role,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Role>(role,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * 修改 基础方法
	 * @param Role
	 * @return
	 * @throws Exception 
	 */
	@PutMapping("/right")
	@PreAuthorize(value="hasRole('role_update')")
	@ApiOperation(value="修改角色", notes="根据role修改角色，需要权限role_update')")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Role",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Role> updateRoleRight(@RequestBody Role role) throws Exception{
		
			if(roleService.updateRoleRight(role)){
				return new  ResponseEntity<Role>(role,HttpStatus.OK);
			}
			
			throw new Exception("更新角色下的权限/菜单失败");
		
	}
	
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('role_info')")
	@ApiOperation(value="查询角色", notes="根据id查询角色，需要权限role_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Role.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Role> findByid(@PathVariable("id")Long id){
		
		
		     Role role=null;
			try {
				role = roleService.selectById(id);
				 
				 if(role !=null) {
					 return new  ResponseEntity<Role>(role,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Role>(new Role(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Role>(new Role(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * 根据id查询角色及权限
	 * @param id
	 * @return
	 */
	@GetMapping(value="/right/{id}/")
	@PreAuthorize(value="hasRole('role_info')")
	@ApiOperation(value="查询角色", notes="根据id查询角色及权限，需要权限role_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),			
			@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Role.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Role> selectRoleRightByid(@PathVariable("id")Long id){
		
		
		     Role role=null;
			try {
				role = roleService.selectRoleRightByid(id);
				 
				 if(role !=null) {
					 return new  ResponseEntity<Role>(role,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Role>(new Role(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Role>(new Role(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Role
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('role_page')")
	@ApiOperation(value="分页查询角色", notes="根据role分页角色，需要权限role_info")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "role", value = "角色", required = true, dataType = "Role",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Role>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Role role){
		Page<Role> page=null;
		try {
			page=new Page<Role>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= roleService.findRoleByPage(page, role);
			
			
			
			return new ResponseEntity<Page<Role>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<Role>>(new Page<Role>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('role_all')")
		@ApiOperation(value="查询角色", notes="根据role查询角色")
		
			@ApiImplicitParams(value={
					@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
						 @ApiImplicitParam(name = "roleName",value = "角色名", required = false, dataType = "String",paramType="query")
												 ,@ApiImplicitParam(name = "intro", value = "简介", required = false, dataType = "String",paramType="query")   
												 ,@ApiImplicitParam(name = "deleted", value = "删除状态", required = false, dataType = "String",paramType="query")   
							})
		
	
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	
	
	
	public ResponseEntity<List<Role>> findRoleAll(
				 @RequestParam(value="roleName",required=false)String roleName
												 ,@RequestParam(value="intro",required=false)String intro
			  									 ,@RequestParam(value="deleted",required=false)String deleted
	){
		Role role =new Role();
						role.setRoleName(roleName);
						role.setIntro(intro);
						role.setDeleted(deleted);
					List<Role> roles= null;
		 try {
			 roles=roleService.findRoleAll(role);
			 return new ResponseEntity<List<Role>>(roles, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Role>>(new ArrayList<Role>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@PostMapping(value="perms")
	@PreAuthorize(value="hasRole('role_perms')")
	@ApiOperation(value="分配权限", notes="为角色分配权限")
	@ApiImplicitParams({
		@ApiImplicitParam(name="roleRights",value="角色权限关系组",required=true,dataType="RoleRight[]",paramType="body"),
		@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query")
		
	}
			)
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)	
			
	})
	public ResponseEntity<String> perms(@RequestBody List<RoleRight> roleRights){
		
		if(roleRightService.perms(roleRights)){
			return new ResponseEntity<String>("成功",HttpStatus.OK);
		}
		return new ResponseEntity<String>("失败",HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
}
