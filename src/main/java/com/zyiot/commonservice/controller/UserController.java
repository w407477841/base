package com.zyiot.commonservice.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.javassist.expr.NewArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import com.zyiot.commonservice.common.util.StringUtil;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.excepion.AddException;
import com.zyiot.commonservice.excepion.DeleteException;
import com.zyiot.commonservice.excepion.ParamException;
import com.zyiot.commonservice.excepion.UpdateException;
import com.zyiot.commonservice.pojo.PUserInfo;
import com.zyiot.commonservice.redis.service.IRedisCodeService;
import com.zyiot.commonservice.redis.service.impl.RedisCodeService;
import com.zyiot.commonservice.service.IAliyunMesageService;
import com.zyiot.commonservice.service.IUserService;
import com.zyiot.commonservice.service.impl.AliyunMesageService;
/**
 * @author 王一飞
 * @since 2017-12-14 08:48:37
 */

@Controller
@RequestMapping("/user")
public class  UserController {
	 @Autowired
	private IUserService  userService;
	

		@Autowired
		private PasswordEncoder   bCryptPasswordEncoder;
		@Autowired
		IAliyunMesageService     aliyunMesageService;
		@Autowired
		IRedisCodeService   redisCodeService; 
		@Value("${aliyun.signName}")
		private String signName;
		@Value("${aliyun.bind.templateCode}")
		private String codeTemplateCode;
		
/**
 * 添加  基础方法
 * @return
 */
	@PostMapping()
	@PreAuthorize(value="hasRole('user_add')")
	@ApiOperation(value="创建用户", notes="根据user创建用户,需要权限user_add")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "User",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< User> add(@RequestBody() User user){
		if(StringUtil.isEmpty(user.getUsername())){
			throw new ParamException("用户名必填");
		}
		if(StringUtil.isEmpty(user.getPassword())){
			throw new ParamException("密码必填");
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			
			if (userService.insert(user)) {
				return new ResponseEntity<User>(user, HttpStatus.OK);
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
	@PreAuthorize(value="hasRole('user_deleted')")
	@ApiOperation(value="删除用户", notes="根据id删除用户，需要权限user_deleted')")
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
	public  ResponseEntity<Object> deleted(@PathVariable(value="id")Long id){
			if(userService.deleteById(id)){
				return new  ResponseEntity<Object>("删除成功",HttpStatus.OK);
			}else{
				throw new DeleteException("删除失败，数据已不存在");
			}
	}
	/**
	 * 修改 基础方法
	 * @param User
	 * @return
	 */
	@PutMapping()
	@PreAuthorize(value="hasRole('user_update')")
	@ApiOperation(value="修改用户", notes="根据user修改用户，需要权限user_update')")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "User",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity< User> update(@RequestBody User user){
		
			if(userService.updateById(user)){
				return new  ResponseEntity<User>(user,HttpStatus.OK);
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
	@PreAuthorize(value="hasRole('user_info')")
	@ApiOperation(value="查询用户", notes="根据id查询用户，需要权限user_info",response=User.class)
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "id", value = "主键", required = true, dataType = "Long",paramType="path")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功"),
			@ApiResponse(code=401,message="未授权,先登录"),
			@ApiResponse(code=403,message="无权限"),
			@ApiResponse(code=404,message="不存在"),
			@ApiResponse(code=500,message="失败")
	})
	public ResponseEntity<User> findByid(@PathVariable("id")Long id){
		     User user=null;
				user = userService.selectById(id);
				 
				 if(user !=null) {
					 return new  ResponseEntity<User>(user,HttpStatus.OK);
				 }else{
				 return new  ResponseEntity<User>(new User(),HttpStatus.OK);
				 }
	}
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param User
	 * @return
	 */
	@PostMapping("/page/")
	@PreAuthorize(value="hasRole('user_page')")
	@ApiOperation(value="分页查询用户", notes="根据user分页用户，需要权限user_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "user", value = "用户", required = true, dataType = "User",paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<User>> fingByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestBody User user){
		Page<User> page=null;
			
			page=new Page<User>(pageNum, pageSize);

			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= userService.findUserByPage(page, user);
			return new ResponseEntity<Page<User>>(page, HttpStatus.OK);
	}
	
	/**
	 * 分页查询  基础方法
	 * @param pageSize
	 * @param pageNum
	 * @param sort
	 * @param column
	 * @param User
	 * @return
	 */
	@PostMapping("/key/")
	@PreAuthorize(value="hasRole('user_page')")
	@ApiOperation(value="分页查询用户", notes="根据key分页用户，需要权限user_info")
	@ApiImplicitParams(value={
	@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
	@ApiImplicitParam(name = "pageSize", value = "每页大小[大于0]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "pageNum", value = "当前页号[1开始]", required = true, dataType = "Integer",paramType="query"),
	@ApiImplicitParam(name = "sort", value = "排序方式[desc/asc]", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "column", value = "排序字段", required = true, dataType = "String",paramType="query"),
	@ApiImplicitParam(name = "key", value = "用户名/真实姓名/状态", required = false, dataType = "String",paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=200,message="成功",response=Page.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Page<User>> fingUseKeyByPage(@RequestParam("pageSize")int pageSize,@RequestParam("pageNum")int pageNum,@RequestParam("sort")String sort ,@RequestParam("column")String column,@RequestParam(value="key",required=false)String key){
		Page<User> page=null;
			page=new Page<User>(pageNum, pageSize);
			page.setAsc(!(sort!=null&&sort.equals("desc")));
			page.setOrderByField(column);
			page.setOpenSort(true);
			page= userService.findOrUserByPage(page,key );
			
			
			
			return new ResponseEntity<Page<User>>(page, HttpStatus.OK);
	}
	
	
		@GetMapping()
		@PreAuthorize(value="hasRole('user_all')")
		@ApiOperation(value="查询用户", notes="根据user查询用户",response=User.class,responseContainer="List")
			@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="登录令牌",required=false,dataType="String",paramType="query"),
									@ApiImplicitParam(name = "username", value = "用户名", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "realname", value = "姓名", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "password", value = "密码", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "accessToken", value = "令牌", required = false, dataType = "String",paramType="query")   
												,@ApiImplicitParam(name = "status", value = "用户状态", required = false, dataType = "String",paramType="query")
												,@ApiImplicitParam(name = "approved", value = "核准状态", required = false, dataType = "String",paramType="query")
							})
		@ApiResponses(value={
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<List<User>> findRoleAll(
				 @RequestParam(value="username",required=false)String username
												 ,@RequestParam(value="realname",required=false)String realname
			  									 ,@RequestParam(value="password",required=false)String password
			  									 ,@RequestParam(value="accessToken",required=false)String accessToken
			  									 ,@RequestParam(value="status",required=false)String status
			  									 ,@RequestParam(value="approved",required=false)String approved
			  				){
		User user =new User();
						user.setUsername(username);
						user.setRealname(realname);
						user.setPassword(password);
						user.setAccessToken(accessToken);
						user.setStatus(status);
						user.setApproved(approved);
					List<User> users= null;
			 users=userService.findUserAll(user);
			 return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
		
	@PutMapping(value="password")
	@PreAuthorize(value="hasRole('user_updpassword')")
	@ApiOperation(value="修改密码",notes="根据user对象修改密码 ")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="user",value="密码修改信息",dataType="User",required=true,paramType="body")
	})
	@ApiResponses(value={
			@ApiResponse(code=400,message="参数缺失",response=String.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<String> updPassword(@RequestBody User user  ){
		if(
				user.getUsername()!=null&&!user.getUsername().equals("")&&
				user.getPassword()!=null&&!user.getPassword().equals("")&&
				user.getOldPassword()!=null&&!user.getOldPassword().equals("")
				){
				userService.updatePassword(user);
				return new ResponseEntity<String>("修改成功",HttpStatus.OK);
		}else{
			throw new UpdateException("参数缺失 username/password/oldPassword");
		}
	}
	
	
	@GetMapping(value="info")
	@ApiOperation(value="用户信息",notes="获取用户详细信息 ")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="username",value="用户名",dataType="String",required=true,paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=400,message="参数缺失",response=String.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<PUserInfo> info(@RequestParam(required=true,value="username")String username  ){

		PUserInfo userInfo= userService.selectUserInfoByUsername(username);
		
		return new ResponseEntity<PUserInfo>(userInfo,HttpStatus.OK);
		
	}
	@PutMapping(value="info")
	@ApiOperation(value="性别、生日、email",notes=" ")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="用户名",dataType="String",required=true,paramType="query"),
			@ApiImplicitParam(name="username",value="用户名",dataType="String",required=true,paramType="query"),
			@ApiImplicitParam(name="sex",value="性别[男、女]",dataType="String",required=false,paramType="query"),
			@ApiImplicitParam(name="birth",value="生日yyyy-MM-dd",dataType="String",required=false,paramType="query"),
			@ApiImplicitParam(name="email",value="电子邮箱",dataType="String",required=false,paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=400,message="参数缺失",response=String.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> UpdateUserinfo(@RequestParam(required=true,value="username")String username,@RequestParam(value="sex",required=false)String sex,@RequestParam(value="birth",required=false)String birth,@RequestParam(value="email",required=false)String email,HttpServletRequest request){
		
		userService.updateUserInfo(username, null, sex, birth,email);
		
		return new ResponseEntity<Object>( null,HttpStatus.OK);
		
	}
	@PutMapping(value="phone")
	@ApiOperation(value="绑定手机",notes=" ")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="access_token",value="用户名",dataType="String",required=true,paramType="query"),
			@ApiImplicitParam(name="username",value="用户名",dataType="String",required=true,paramType="query"),
			@ApiImplicitParam(name="phone",value="手机号",dataType="String",required=true,paramType="query"),
			@ApiImplicitParam(name="code",value="验证码",dataType="String",required=true,paramType="query")
	})
	@ApiResponses(value={
			@ApiResponse(code=400,message="参数缺失",response=String.class),
			@ApiResponse(code=401,message="未授权,先登录",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> UpdatePhone(@RequestParam(required=true,value="username")String username,@RequestParam(value="phone",required=true)String phone,@RequestParam(value="code",required=true)String code){
		
		String cachecode=redisCodeService.getCode(RedisCodeService.BIND, phone);
		if(cachecode==null) throw new RuntimeException("验证码过期");
		
		if(StringUtil.isEq(cachecode, code)){
			
			userService.updateUserInfo(username, phone, null, null,null);
			
			redisCodeService.clearCode(RedisCodeService.BIND, phone);
			
		}else{
			throw new RuntimeException("验证码错误");
		}
		return new ResponseEntity<Object>( null,HttpStatus.OK);
		
	}
	@GetMapping(value="phoneCode")
	@ApiOperation(value="绑定手机时，获取验证码",notes=" 提供 phone",httpMethod="GET")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="phone",value="手机号",paramType="query",dataType="String",required=true)
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="登录失败",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> phoneCode(@RequestParam(value="phone",required=true)String phone){
		//验证手机是否存在
		userService.isExist(phone);
		//向阿里云平台获取验证码
			String	code = aliyunMesageService.getCode(signName, codeTemplateCode, phone);//此处可能会抛异常，后面更新缓存代码就不会执行
			redisCodeService.updCode(RedisCodeService.BIND,phone,code);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
}
