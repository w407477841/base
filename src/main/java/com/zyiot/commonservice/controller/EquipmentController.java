package com.zyiot.commonservice.controller;

import java.util.ArrayList;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Date;

import com.zyiot.commonservice.excepion.AddException;
import com.zyiot.commonservice.excepion.DeleteException;
import com.zyiot.commonservice.excepion.UpdateException;

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
import com.zyiot.commonservice.entity.Equipment;
import com.zyiot.commonservice.service.IEquipmentService;
/**
 * @author 王一飞
 * @since 2018-05-08 16:58:14
 */

@Controller
@RequestMapping("/equipment")
public class  EquipmentController {
	 @Autowired
	private IEquipmentService  equipmentService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('equipment_add')")
	@ApiOperation(value="创建PLC设备管理", notes="根据equipment创建PLC设备管理,需要权限equipment_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "equipment", value = "PLC设备管理", required = true, dataType = "Equipment",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Equipment> add(@RequestBody() Equipment equipment){
			if (equipmentService.insert(equipment)) {
				return new ResponseEntity<Equipment>(equipment, HttpStatus.OK);
		}else{
				throw new  AddException("未知失败，请重试");
			}
		
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('equipment_deleted')")
	@ApiOperation(value="删除PLC设备管理", notes="根据id删除PLC设备管理，需要权限equipment_deleted')")
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
			if(equipmentService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
		}else{
				throw new DeleteException("删除失败，数据已不存在");
			}
		
		
	}
	/**
	 * 修改 基础方法
	 * @param Equipment
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('equipment_update')")
	@ApiOperation(value="修改PLC设备管理", notes="根据equipment修改PLC设备管理，需要权限equipment_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "equipment", value = "PLC设备管理", required = true, dataType = "Equipment",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< Equipment> update(@RequestBody Equipment equipment){
		
			if(equipmentService.updateById(equipment)){
				return new  ResponseEntity<Equipment>(equipment,HttpStatus.OK);
		}else{
				throw new UpdateException("数据不存在");
			}
		
	}
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('equipment_info')")
	@ApiOperation(value="查询PLC设备管理", notes="根据id查询PLC设备管理，需要权限equipment_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Equipment.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Equipment> findByid(@PathVariable("id")Long id){
		
		
		     Equipment equipment=null;
				equipment = equipmentService.selectById(id);
				 
				 if(equipment !=null) {
					 return new  ResponseEntity<Equipment>(equipment,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<Equipment>(new Equipment(),HttpStatus.OK);
				 }
		     
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Equipment
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('equipment_page')")
	@ApiOperation(value="分页查询PLC设备管理", notes="根据equipment分页PLC设备管理，需要权限equipment_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "equipment", value = "PLC设备管理", required = true, dataType = "Equipment",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<Equipment>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody Equipment equipment){
		Page<Equipment> page=null;
			page=new Page<Equipment>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= equipmentService.findEquipmentByPage(page, equipment);
			
			
			
			return new ResponseEntity<Page<Equipment>>(page, HttpStatus.OK);
	}
	
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param Equipment
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('equipment_page')")
	@ApiOperation(value="分页查询PLC设备管理", notes="根据key分页PLC设备管理，需要权限equipment_info")
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
	public ResponseEntity<Page<Equipment>> fingUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam (value="key",required=false)String key){
		Page<Equipment> page=null;
			page=new Page<Equipment>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= equipmentService.findOrEquipmentByPage(page, key);
			
			
			
			return new ResponseEntity<Page<Equipment>>(page, HttpStatus.OK);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('equipment_all')")
		@ApiOperation(value="查询PLC设备管理", notes="根据equipment查询PLC设备管理")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "key", value = "设备唯一标识", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "name", value = "设备名称", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "factoryId", value = "所属工厂", required = false, dataType = "Long",paramType="query")   
												,@ApiImplicitParam(name = "type", value = "类型", required = false, dataType = "String",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<Equipment>> findRoleAll(
				 @RequestParam(value="key",required=false)String key
												 ,@RequestParam(value="name",required=false)String name
			  									 ,@RequestParam(value="factoryId",required=false)Long factoryId
			  									 ,@RequestParam(value="type",required=false)String type
			  				){
		Equipment equipment =new Equipment();
						equipment.setKey(key);
						equipment.setName(name);
						equipment.setFactoryId(factoryId);
						equipment.setType(type);
					List<Equipment> equipments= null;
			 equipments=equipmentService.findEquipmentAll(equipment);
			 return new ResponseEntity<List<Equipment>>(equipments, HttpStatus.OK);
	}
	
	
	
	
}
