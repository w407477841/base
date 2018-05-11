package com.zyiot.commonservice.redis.service.impl;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RedisTest {
@Cacheable(value="test1",key="#key")
	public String test1(String key){
		System.out.println("<<<<test1不存在"+key+">>>");
		return key;
	}
@Cacheable(value="test2",key="#key")
public String test2(String key){
	System.out.println("<<<<test2不存在"+key+">>>");
	return key;
}	
}
