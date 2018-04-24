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
import org.springframework.beans.factory.annotation.Value;
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
import com.zyiot.commonservice.entity.ShangjiInfo;
import com.zyiot.commonservice.service.IShangjiInfoService;
/**
 * @author 王一飞
 * @since 2018-03-06 15:46:30
 */

@Controller
@RequestMapping("/shangjiInfo")
public class  ShangjiInfoController {
	 @Autowired
	private IShangjiInfoService  shangjiInfoService;
	 @Value("${flow.shangji}")
	private String processDefinitionId; 
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('shangjiInfo_add')")
	@ApiOperation(value="创建商机信息", notes="根据shangjiInfo创建商机信息,需要权限shangjiInfo_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "shangjiInfo", value = "商机信息", required = true, dataType = "ShangjiInfo",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< ShangjiInfo> add(@RequestBody() ShangjiInfo shangjiInfo){
		try {
			//if (shangjiInfoService.insertStart(shangjiInfo)) {
				if (shangjiInfoService.flowStartShangji(shangjiInfo, shangjiInfo.getYg(), processDefinitionId)!=null) {
				return new ResponseEntity<ShangjiInfo>(shangjiInfo, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new  ResponseEntity< ShangjiInfo>( shangjiInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new  ResponseEntity< ShangjiInfo>( shangjiInfo,HttpStatus.BAD_REQUEST);
	}
	/**
	 * 删除 基础方法
	 * @param id
	 * @return
	 */
	@DeleteMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('shangjiInfo_deleted')")
	@ApiOperation(value="删除商机信息", notes="根据id删除商机信息，需要权限shangjiInfo_deleted')")
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
			if(shangjiInfoService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new  ResponseEntity<String>("删除失败",HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}
	/**
	 * 修改 基础方法
	 * @param ShangjiInfo
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('shangjiInfo_update')")
	@ApiOperation(value="修改商机信息", notes="根据shangjiInfo修改商机信息，需要权限shangjiInfo_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "shangjiInfo", value = "商机信息", required = true, dataType = "ShangjiInfo",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< ShangjiInfo> update(@RequestBody ShangjiInfo shangjiInfo){
		
		try {
			if(shangjiInfoService.updateById(shangjiInfo)){
				return new  ResponseEntity<ShangjiInfo>(shangjiInfo,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new  ResponseEntity< ShangjiInfo>(shangjiInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@PutMapping("complete")
	@PreAuthorize(value="hasRole('shangjiInfo_update')")
	@ApiOperation(value="修改商机信息", notes="根据shangjiInfo修改商机信息，需要权限shangjiInfo_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name="taskId",value="任务编号",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "shangjiInfo", value = "商机信息", required = true, dataType = "ShangjiInfo",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})	
public ResponseEntity< ShangjiInfo> complete(@RequestBody ShangjiInfo shangjiInfo,@RequestParam("taskId")String taskId){
		
		try {
			if(shangjiInfoService.flowCompleteShangji(shangjiInfo, taskId)){
				return new  ResponseEntity<ShangjiInfo>(shangjiInfo,HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new  ResponseEntity< ShangjiInfo>(shangjiInfo,HttpStatus.BAD_REQUEST);
		}
		return new  ResponseEntity< ShangjiInfo>(shangjiInfo,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	
	/**
	 * 单条查询 基础方法
	 * @param id
	 * @return
	 */
	@GetMapping(value="/{id}/")
	@PreAuthorize(value="hasRole('shangjiInfo_info')")
	@ApiOperation(value="查询商机信息", notes="根据id查询商机信息，需要权限shangjiInfo_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=ShangjiInfo.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<ShangjiInfo> findByid(@PathVariable("id")Long id){
		
		
		     ShangjiInfo shangjiInfo=null;
			try {
				shangjiInfo = shangjiInfoService.selectById(id);
				 
				 if(shangjiInfo !=null) {
					 return new  ResponseEntity<ShangjiInfo>(shangjiInfo,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<ShangjiInfo>(new ShangjiInfo(),HttpStatus.OK);
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     
		     return new  ResponseEntity<ShangjiInfo>(new ShangjiInfo(),HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param ShangjiInfo
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('shangjiInfo_page')")
	@ApiOperation(value="分页查询商机信息", notes="根据shangjiInfo分页商机信息，需要权限shangjiInfo_info")
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
	public ResponseEntity<Page<ShangjiInfo>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody ShangjiInfo shangjiInfo){
		Page<ShangjiInfo> page=null;
		try {
			page=new Page<ShangjiInfo>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= shangjiInfoService.findShangjiInfoByPage(page, shangjiInfo);
			
			
			
			return new ResponseEntity<Page<ShangjiInfo>>(page, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  return new  ResponseEntity<Page<ShangjiInfo>>(new Page<ShangjiInfo>(pageNum, pageSize),HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
		@GetMapping()
		@PreAuthorize(value="hasRole('shangjiInfo_all')")
		@ApiOperation(value="查询商机信息", notes="根据shangjiInfo查询商机信息")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "sbsj", value = "申报时间", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "yg", value = "填报人", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "xmbh", value = "项目编号", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "khqc", value = "客户全称", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "khlxr", value = "客户联系人", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "lxfs", value = "联系方式", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "xmmc", value = "项目名称", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "sjly", value = "商机来源", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "yshtje", value = "预算合同金额(元)", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "shzt", value = "审核状态", required = false, dataType = "Integer",paramType="query")   
												,@ApiImplicitParam(name = "gszt", value = "公示状态", required = false, dataType = "Integer",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<ShangjiInfo>> findRoleAll(
				 @RequestParam(value="sbsj",required=false)String sbsj
												 ,@RequestParam(value="yg",required=false)String yg
			  									 ,@RequestParam(value="xmbh",required=false)String xmbh
			  									 ,@RequestParam(value="khqc",required=false)String khqc
			  									 ,@RequestParam(value="khlxr",required=false)String khlxr
			  									 ,@RequestParam(value="lxfs",required=false)String lxfs
			  									 ,@RequestParam(value="xmmc",required=false)String xmmc
			  									 ,@RequestParam(value="sjly",required=false)String sjly
			  									 ,@RequestParam(value="yshtje",required=false)String yshtje
			  									 ,@RequestParam(value="shzt",required=false)Integer shzt
			  									 ,@RequestParam(value="gszt",required=false)Integer gszt
			  				){
		ShangjiInfo shangjiInfo =new ShangjiInfo();
						shangjiInfo.setSbsj(sbsj);
						shangjiInfo.setYg(yg);
						shangjiInfo.setXmbh(xmbh);
						shangjiInfo.setKhqc(khqc);
						shangjiInfo.setKhlxr(khlxr);
						shangjiInfo.setLxfs(lxfs);
						shangjiInfo.setXmmc(xmmc);
						shangjiInfo.setSjly(sjly);
						shangjiInfo.setYshtje(yshtje);
						shangjiInfo.setShzt(shzt);
						shangjiInfo.setGszt(gszt);
					List<ShangjiInfo> shangjiInfos= null;
		 try {
			 shangjiInfos=shangjiInfoService.findShangjiInfoAll(shangjiInfo);
			 return new ResponseEntity<List<ShangjiInfo>>(shangjiInfos, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return new ResponseEntity<List<ShangjiInfo>>(new ArrayList<ShangjiInfo>(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
}
