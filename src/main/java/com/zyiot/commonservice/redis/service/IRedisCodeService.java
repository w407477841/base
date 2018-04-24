package com.zyiot.commonservice.redis.service;


public interface IRedisCodeService {
	
	public String getCode(String value,String phoneNumber);
	public String updCode(String value,String phoneNumber,String code);
	public String clearCode(String value,String phoneNumber);
}
