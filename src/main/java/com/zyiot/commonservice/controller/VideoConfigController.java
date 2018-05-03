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
import com.zyiot.commonservice.entity.VideoConfig;
import com.zyiot.commonservice.excepion.AddException;
import com.zyiot.commonservice.excepion.DeleteException;
import com.zyiot.commonservice.excepion.UpdateException;
import com.zyiot.commonservice.service.IVideoConfigService;
/**
 * @author 王一飞
 * @since 2018-05-03 13:56:11
 */

@Controller
@RequestMapping("/videoConfig")
public class  VideoConfigController {
	 @Autowired
	private IVideoConfigService  videoConfigService;
	
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('videoConfig_add')")
	@ApiOperation(value="创建视频配置管理", notes="根据videoConfig创建视频配置管理,需要权限videoConfig_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "videoConfig", value = "视频配置管理", required = true, dataType = "VideoConfig",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< VideoConfig> add(@RequestBody() VideoConfig videoConfig){
			if (videoConfigService.insert(videoConfig)) {
				return new ResponseEntity<VideoConfig>(videoConfig, HttpStatus.OK);
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
	@PreAuthorize(value="hasRole('videoConfig_deleted')")
	@ApiOperation(value="删除视频配置管理", notes="根据id删除视频配置管理，需要权限videoConfig_deleted')")
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
			if(videoConfigService.deleteById(id)){
				return new  ResponseEntity<String>("删除成功",HttpStatus.OK);
		}else{
				throw new DeleteException("删除失败，数据已不存在");
			}
		
		
	}
	/**
	 * 修改 基础方法
	 * @param VideoConfig
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('videoConfig_update')")
	@ApiOperation(value="修改视频配置管理", notes="根据videoConfig修改视频配置管理，需要权限videoConfig_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "videoConfig", value = "视频配置管理", required = true, dataType = "VideoConfig",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< VideoConfig> update(@RequestBody VideoConfig videoConfig){
		
			if(videoConfigService.updateById(videoConfig)){
				return new  ResponseEntity<VideoConfig>(videoConfig,HttpStatus.OK);
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
	@PreAuthorize(value="hasRole('videoConfig_info')")
	@ApiOperation(value="查询视频配置管理", notes="根据id查询视频配置管理，需要权限videoConfig_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=VideoConfig.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<VideoConfig> findByid(@PathVariable("id")Long id){
		
		
		     VideoConfig videoConfig=null;
				videoConfig = videoConfigService.selectById(id);
				 
				 if(videoConfig !=null) {
					 return new  ResponseEntity<VideoConfig>(videoConfig,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<VideoConfig>(new VideoConfig(),HttpStatus.OK);
				 }
		     
		
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param VideoConfig
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('videoConfig_page')")
	@ApiOperation(value="分页查询视频配置管理", notes="根据videoConfig分页视频配置管理，需要权限videoConfig_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "videoConfig", value = "视频配置管理", required = true, dataType = "VideoConfig",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<VideoConfig>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody VideoConfig videoConfig){
		Page<VideoConfig> page=null;
			page=new Page<VideoConfig>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= videoConfigService.findVideoConfigByPage(page, videoConfig);
			
			
			
			return new ResponseEntity<Page<VideoConfig>>(page, HttpStatus.OK);
	}
	
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param VideoConfig
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('videoConfig_page')")
	@ApiOperation(value="分页查询视频配置管理", notes="根据key分页视频配置管理，需要权限videoConfig_info")
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
	public ResponseEntity<Page<VideoConfig>> fingUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam (value="key",required=false)String key){
		Page<VideoConfig> page=null;
			page=new Page<VideoConfig>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= videoConfigService.findOrVideoConfigByPage(page, key);
			
			
			
			return new ResponseEntity<Page<VideoConfig>>(page, HttpStatus.OK);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('videoConfig_all')")
		@ApiOperation(value="查询视频配置管理", notes="根据videoConfig查询视频配置管理")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "ipAddr", value = "IP地址", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "port", value = "端口号", required = false, dataType = "Integer",paramType="query")   
												,@ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "videoName", value = "视频名称", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "factoryId", value = "工厂编号", required = false, dataType = "Long",paramType="query")   
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<VideoConfig>> findRoleAll(
				 @RequestParam(value="ipAddr",required=false)String ipAddr
												 ,@RequestParam(value="port",required=false)Integer port
			  									 ,@RequestParam(value="username",required=false)String username
			  									 ,@RequestParam(value="password",required=false)String password
			  									 ,@RequestParam(value="videoName",required=false)String videoName
			  									 ,@RequestParam(value="factoryId",required=false)Long factoryId
			  				){
		VideoConfig videoConfig =new VideoConfig();
						videoConfig.setIpAddr(ipAddr);
						videoConfig.setPort(port);
						videoConfig.setUsername(username);
						videoConfig.setPassword(password);
						videoConfig.setVideoName(videoName);
						videoConfig.setFactoryId(factoryId);
					List<VideoConfig> videoConfigs= null;
			 videoConfigs=videoConfigService.findVideoConfigAll(videoConfig);
			 return new ResponseEntity<List<VideoConfig>>(videoConfigs, HttpStatus.OK);
	}
	
	
	
	
}
