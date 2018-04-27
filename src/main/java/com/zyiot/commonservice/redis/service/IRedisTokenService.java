package com.zyiot.commonservice.redis.service;

public interface IRedisTokenService {
	public String getCode(String username);
	public String updCode(String username,String token);
	public void clearCode(String username);
}
