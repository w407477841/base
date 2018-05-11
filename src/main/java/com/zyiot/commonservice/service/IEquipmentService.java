package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Equipment;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-05-08 16:58:14
 */
public interface IEquipmentService extends IService<Equipment> {

	public Page<Equipment> findEquipmentByPage(Page<Equipment> page,Equipment equipment);
	public Page<Equipment> findOrEquipmentByPage(Page<Equipment> page,String key);
	public List<Equipment> findEquipmentAll(Equipment equipment);
	
	
}
