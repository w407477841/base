package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Log;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-14 15:16:13
 */
public interface ILogService extends IService<Log> {

	public Page<Log> findLogByPage(Page<Log> page,Log log);
	
	public List<Log> findLogAll(Log log);
	
	
}
