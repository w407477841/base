package com.zyiot.commonservice.redis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zyiot.commonservice.entity.Equipment;
import com.zyiot.commonservice.redis.service.IRedisDataKeyService;
import com.zyiot.commonservice.service.IEquipmentService;
@Service
public class RedisDataKeyService implements IRedisDataKeyService {
	@Autowired
	IEquipmentService  equipmentService;
	
	@Cacheable(value="datakey",key="#key")
	public String get(String key) {
		System.out.println("<<<<<<<<<<缓存中不存在datakey"+key+">>>>>>>>>>>>>");
		return null;
	}
	@Cacheable(value="equipment",key="'key:'+#key") 
	//@Cacheable(value="equipment",key="#key")
	public Equipment getEquipment(String key) {
		System.out.println("<<<<<<<<<<缓存中不存在equipment"+key+">>>>>>>>>>>>>");
		Wrapper<Equipment> wrapper=new EntityWrapper<Equipment>();
		wrapper.eq("`key`", key);
		return equipmentService.selectOne(wrapper);
	}
	
	
	
	@CachePut(value="datakey",key="#key")
	public String update(String key) {
		return key;
	}

	@CacheEvict(value="datakey",key="#key")
	public void clear(String key) {

	}


	


	@CachePut(value="equipment",key="'key:'+#key")
	public Equipment updateEquipment(String key,Equipment equipment) {
		// TODO Auto-generated method stub
		return equipment;
	}


	@CacheEvict(value="equipment",key="'key:'+#key")
	public void clearEquipment(String key) {
		// TODO Auto-generated method stub
		
	}
	

}
