package com.zyiot.commonservice.redis.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.zyiot.commonservice.redis.service.IRedisTokenService;

/**
 * 
 * @author Administrator
 * 缓存 登录令牌，减少数据库操作次数
 */
@Service
public class RedisTokenService implements IRedisTokenService {

	@Cacheable(value="token",key="#username")
	//表示从缓存中取，如果缓存中没有，则执行方法
	public String getCode(String username) {
		//缓存中获取不到后，返回NULL
		return null;
	}

	@CachePut(value="token",key="#username")
	public String updCode(String username, String token) {
		// 不管缓存中有没有 都会更新缓存
		
		return token;
	}

	@CacheEvict(value="token",key="#username")
	public void clearCode(String username) {
		// 不管缓存中有没有 都会清空
	}

}
