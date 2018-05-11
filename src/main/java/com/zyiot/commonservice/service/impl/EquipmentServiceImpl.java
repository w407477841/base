package com.zyiot.commonservice.service.impl;

import java.util.List;

import com.zyiot.commonservice.entity.Equipment;
import com.zyiot.commonservice.mapper.EquipmentMapper;
import com.zyiot.commonservice.service.IEquipmentService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2018-05-08 16:58:14
 */
@Service
public class EquipmentServiceImpl extends ServiceImpl<EquipmentMapper, Equipment> implements IEquipmentService {

	@Override
	public Page<Equipment> findEquipmentByPage(Page<Equipment> page, Equipment equipment) {
		return  page.setRecords(baseMapper.selectEquipmentByPage(page, equipment));
	}
	
	@Override
	public Page<Equipment> findOrEquipmentByPage(Page<Equipment> page, String key) {
		return  page.setRecords(baseMapper.selectOrEquipmentByPage(page, key));
	}
	
	@Override
	public List<Equipment> findEquipmentAll(Equipment equipment) {
		return baseMapper.selectEquipmentAll(equipment);
	}

}