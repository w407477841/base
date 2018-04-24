package com.zyiot.commonservice.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zyiot.commonservice.redis.service.IRedisCodeService;
import com.zyiot.commonservice.service.IAliyunMesageService;
/**
 * 
 * 验证码相关方法
 *
 */
@Service("redisCodeService")
public class RedisCodeService implements IRedisCodeService {
/**
 * 绑定手机
 */
	public static final String BIND="bind";
/**
 * 注册	
 */
	public static final String REGISTER="register";
/**
 * 登录	
 */
	public static final String LOGIN="login";
	/**
	 * 从缓存中获取，如果缓存中不存在返回null 
	 */
	@Cacheable(value="code",key="#value+#phoneNumber")
	public String getCode(String value,String phoneNumber) {
		
		
		
		return null;
	}
	/**
	 * 更新缓存
	 */
	@CachePut(value="code",key="#value+#phoneNumber")
	public String updCode(String value,String phoneNumber,String code) {
		//当调用调用aliyun服务出现异常，缓存会清空
	//String	code = aliyunMesageService.getCode(signName, templateCode, phoneNumber);
		return code;
	}
	@CachePut(value="code",key="#value+#phoneNumber")
   public String clearCode(String value,String phoneNumber){
	   return null;
   }	
}
