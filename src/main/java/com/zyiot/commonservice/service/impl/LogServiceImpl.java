package com.zyiot.commonservice.service.impl;

import java.util.List;

import com.zyiot.commonservice.entity.Log;
import com.zyiot.commonservice.mapper.LogMapper;
import com.zyiot.commonservice.service.ILogService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2017-12-14 15:16:13
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements ILogService {

	@Override
	public Page<Log> findLogByPage(Page<Log> page, Log log) {
		return  page.setRecords(baseMapper.selectLogByPage(page, log));
	}
	@Override
	public List<Log> findLogAll(Log log) {
		return baseMapper.selectLogAll(log);
	}

}