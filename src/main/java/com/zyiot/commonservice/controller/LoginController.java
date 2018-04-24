package com.zyiot.commonservice.controller;

import java.util.List;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zyiot.commonservice.common.util.StringUtil;
import com.zyiot.commonservice.entity.RegistrationCode;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.pojo.PRegisterInfo;
import com.zyiot.commonservice.redis.service.IRedisCodeService;
import com.zyiot.commonservice.redis.service.impl.RedisCodeService;
import com.zyiot.commonservice.service.IAliyunMesageService;
import com.zyiot.commonservice.service.IRegistrationCodeService;
import com.zyiot.commonservice.service.IUserService;

@RestController
@Api()
public class LoginController {
	@Autowired
	IRegistrationCodeService registrationCodeService;
	@Autowired
	IRedisCodeService redisCodeService ;
	@Autowired
	private IAliyunMesageService aliyunMesageService ;
	@Autowired
	private IUserService  userService;
	
	//签名
	@Value("${aliyun.signName}")
	private String signName;
   @Value("${aliyun.code.templateCode}")
	private String codeTemplateCode;
   @Value("${aliyun.login.templateCode}")
  	private String loginTemplateCode;
	
@ApiOperation(value="用户登录",notes=" 提供 username password",httpMethod="POST")
@ApiImplicitParams(value={
		@ApiImplicitParam(name="user",value="登录信息",paramType="body",dataType="User",required=true)
})
@ApiResponses(value={
		@ApiResponse(code=401,message="登录失败",response=String.class),
		@ApiResponse(code=403,message="无权限",response=String.class),
		@ApiResponse(code=404,message="不存在",response=String.class),
		@ApiResponse(code=500,message="失败",response=String.class)
})

@PostMapping(value="login")
	public ResponseEntity<loginResult>  login (@RequestBody User user ){
		
		
		return new ResponseEntity<loginResult>(new loginResult(),HttpStatus.OK) ;
	}
	@GetMapping(value="validRegisterCode")
	@ApiOperation(value="验证注册码",notes=" 提供 registerCode",httpMethod="GET")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="registerCode",value="注册码",paramType="query",dataType="String",required=true)
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="登录失败",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> validRegisterCode(@RequestParam(value="registerCode",required=true)String registerCode){
		Wrapper<RegistrationCode> wrapper =new EntityWrapper<RegistrationCode>();
		wrapper.eq("code", registerCode);
		List<RegistrationCode> list= registrationCodeService.selectList(wrapper);
		
		if(list!=null&&list.size()>0 ){
			RegistrationCode code=list.get(0);
			if(code.getStatus().equals("未使用")){
			return new ResponseEntity<Object>(HttpStatus.OK);
			}else{
			throw new RuntimeException("注册码已被使用");	
			}
		}else{
			throw new RuntimeException("注册码不存在");
		}
	}	
	
	@GetMapping(value="phoneCode")
	@ApiOperation(value="手机验证码",notes=" 提供 phone",httpMethod="GET")
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
			redisCodeService.updCode(RedisCodeService.REGISTER,phone,code);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	@GetMapping(value="loginCode")
	@ApiOperation(value="登录验证码",notes=" 提供 phone",httpMethod="GET")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="phone",value="手机号",paramType="query",dataType="String",required=true)
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="登录失败",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> loginCode(@RequestParam(value="phone",required=true)String phone){
		/**
		 * 向阿里云平台获取验证码	
		 */
		
		String	code = aliyunMesageService.getCode(signName, loginTemplateCode, phone);//此处可能会抛异常，后面更新缓存代码就不会执行
		
		redisCodeService.updCode(RedisCodeService.LOGIN,phone,code);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	@GetMapping(value="validCode")
	@ApiOperation(value="验证手机+验证码是否匹配",notes=" 提供 phone +code",httpMethod="GET")
	@ApiImplicitParams(value={
			@ApiImplicitParam(name="phone",value="手机号",paramType="query",dataType="String",required=true)
			,@ApiImplicitParam(name="code",value="验证码",paramType="query",dataType="String",required=true)
	})
	@ApiResponses(value={
			@ApiResponse(code=401,message="登录失败",response=String.class),
			@ApiResponse(code=403,message="无权限",response=String.class),
			@ApiResponse(code=404,message="不存在",response=String.class),
			@ApiResponse(code=500,message="失败",response=String.class)
	})
	public ResponseEntity<Object> validCode(@RequestParam(value="phone",required=true)String phone,@RequestParam(value="code",required=true)String code){
	
		String old= redisCodeService.getCode(RedisCodeService.REGISTER,phone);
		//System.out.println("old="+old);
		if(old==null){
			throw new RuntimeException("验证码已过期");
		}
		if(!StringUtil.isEq(old, code)){
			throw new RuntimeException("验证码错误");
		}
		
		redisCodeService.clearCode("register",phone);
		
		return new ResponseEntity<Object>(HttpStatus.OK);
	}
	
	
	
	



		@PostMapping(value="register")
		@ApiOperation(value="注册",notes=" 注册",httpMethod="POST")
		@ApiImplicitParams(value={
				@ApiImplicitParam(name="registerInfo",value="注册信息",paramType="body",dataType="PRegisterInfo",required=true)
		})
		@ApiResponses(value={
				@ApiResponse(code=401,message="登录失败",response=String.class),
				@ApiResponse(code=403,message="无权限",response=String.class),
				@ApiResponse(code=404,message="不存在",response=String.class),
				@ApiResponse(code=500,message="失败",response=String.class)
		})
		public ResponseEntity<Object> register(@RequestBody PRegisterInfo registerInfo){
			userService.insertRegister(registerInfo);
			return new ResponseEntity<Object>(HttpStatus.OK);
		}	
	
	
}



class loginResult {
	
	private String status ;
	private String access_token;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	
	
	
}