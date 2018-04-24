package com.zyiot.commonservice.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zyiot.commonservice.entity.Factory;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.mapper.FactoryMapper;
import com.zyiot.commonservice.mapper.UserMapper;
import com.zyiot.commonservice.service.IFactoryService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2018-03-28 13:53:47
 */
@Service
public class FactoryServiceImpl extends ServiceImpl<FactoryMapper, Factory> implements IFactoryService {
@Autowired
	private UserMapper userMapper;
	
	@Override
	public Page<Factory> findFactoryByPage(Page<Factory> page, Factory factory) {
		return  page.setRecords(baseMapper.selectFactoryByPage(page, factory));
	}
	
	@Override
	public Page<Factory> findOrFactoryByPage(Page<Factory> page, String key) {
		return  page.setRecords(baseMapper.selectOrFactoryByPage(page, key));
	}
	
	@Override
	public List<Factory> findFactoryAll(Factory factory) {
		return baseMapper.selectFactoryAll(factory);
	}

	@Override
	public Factory selectById(Serializable id) {
		
		Factory f =baseMapper.selectById(id);
		
		f.setOwners(userMapper.selectALlMaps());
		
		return f;
	}
	
	

}