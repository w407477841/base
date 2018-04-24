package com.zyiot.commonservice.service.impl;

import java.util.List;

import com.zyiot.commonservice.entity.Right;
import com.zyiot.commonservice.mapper.RightMapper;
import com.zyiot.commonservice.service.IRightService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2017-12-13 13:46:24
 */
@Service
public class RightServiceImpl extends ServiceImpl<RightMapper, Right> implements IRightService {

	@Override
	public Page<Right> findRightByPage(Page<Right> page, Right right) {
		return  page.setRecords(baseMapper.selectRightByPage(page, right));
	}
	@Override
	public List<Right> findRightAll(Right right) {
		return baseMapper.selectRightAll(right);
	}
	@Override
	public List<Right> findByModuleAndUsername(Long moduleId, String username) {
		return baseMapper.findByModuleAndUsername(moduleId, username);
	}

}