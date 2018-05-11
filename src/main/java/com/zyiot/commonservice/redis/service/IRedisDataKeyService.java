package com.zyiot.commonservice.redis.service;

import com.zyiot.commonservice.entity.Equipment;

public interface IRedisDataKeyService {
	public String get(String key);
	public String update(String key);
	public void clear(String key);
	
	public Equipment getEquipment(String key);
	
	public Equipment updateEquipment(String key,Equipment equipment);
	
	public void clearEquipment(String key);
	
}
