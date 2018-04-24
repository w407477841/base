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
import com.zyiot.commonservice.entity.Menu;
import com.zyiot.commonservice.service.IMenuService;
/**
 * @author 王一飞
 * @since 2018-03-15 09:10:10
 */

@Controller
@RequestMapping("/menu")
public class  MenuController {
	 @Autowired
	private IMenuService  menuService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('menu_add')")
	@ApiOperation(value="创建菜单", notes="根据menu创建菜单,需要权限menu_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "menu", value = "菜单", required = true, dataType = "Menu",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Menu> add(@RequestBody() Menu menu){
		try {
			if (menuService.insert(menu)) {
				return new ResponseEntity<Menu>(menu, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity< Menu>( menu,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('menu_deleted')")
	@ApiOperation(value="删除菜单", notes="根据id删除菜单，需要权限menu_deleted')")
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
			if(menuService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Menu
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('menu_update')")
	@ApiOperation(value="修改菜单", notes="根据menu修改菜单，需要权限menu_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "menu", value = "菜单", required = true, dataType = "Menu",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Menu> update(@RequestBody Menu menu){
		
		try {
			if(menuService.updateById(menu)){
				return new  ResponseEntity<Menu>(menu,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< Menu>(menu,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('menu_info')")
	@ApiOperation(value="查询菜单", notes="根据id查询菜单，需要权限menu_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Menu.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Menu> findByid(@PathVariable("id")Long id){
		
		
		     Menu menu=null;
			try {
				menu = menuService.selectById(id);
				 
				 if(menu !=null) {
					 return new  ResponseEntity<Menu>(menu,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Menu>(new Menu(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<Menu>(new Menu(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Menu
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('menu_page')")
	@ApiOperation(value="分页查询菜单", notes="根据menu分页菜单，需要权限menu_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "menu", value = "菜单", required = true, dataType = "Menu",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Menu>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Menu menu){
		Page<Menu> page=null;
			page=new Page<Menu>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= menuService.findMenuByPage(page, menu);
			return new ResponseEntity<Page<Menu>>(page, HttpStatus.OK);
	}
	
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Menu
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('menu_page')")
	@ApiOperation(value="分页查询菜单", notes="根据key分页菜单，需要权限menu_page")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "key", value = "key", required = false, dataType = "String",paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Menu>> findUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam("key") String key){
		Page<Menu> page=null;
			page=new Page<Menu>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= menuService.findOrMenuByPage(page, key);
			return new ResponseEntity<Page<Menu>>(page, HttpStatus.OK);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('menu_all')")
		@ApiOperation(value="查询菜单", notes="根据menu查询菜单")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "url", value = "菜单地址", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "txt", value = "菜单内容", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "status", value = "菜单状态", required = false, dataType = "Integer",paramType="query")   
												,@ApiImplicitParam(name = "pid", value = "父级菜单", required = false, dataType = "Long",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<Menu>> findRoleAll(
				 @RequestParam(value="url",required=false)String url
												 ,@RequestParam(value="txt",required=false)String txt
			  									 ,@RequestParam(value="status",required=false)Integer status
			  									 ,@RequestParam(value="pid",required=false)Long pid
			  				){
		Menu menu =new Menu();
						menu.setUrl(url);
						menu.setTxt(txt);
						menu.setStatus(status);
						menu.setPid(pid);
					List<Menu> menus= null;
		 try {
			 menus=menuService.findMenuAll(menu);
			 return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Menu>>(new ArrayList<Menu>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping("/sort")
		@PreAuthorize(value="hasRole('menu_all')")
		@ApiOperation(value="查询菜单", notes="根据用户名查询菜单,并有父子关系")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query")
												
												,@ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=400,message="服务器错误",response=String.class)
	})
	public ResponseEntity<List<Menu>> findRoleAllSort(
			  									
			  									 @RequestParam(value="username",required=true)String username
			  				){
					List<Menu> menus= null;
		 try {
			 menus=menuService.findRoleAllSort(username);
			 
			 return new ResponseEntity<List<Menu>>(menus, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<Menu>>(new ArrayList<Menu>(), HttpStatus.BAD_REQUEST);
	}
	
	
}
