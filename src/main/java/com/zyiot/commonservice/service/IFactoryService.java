package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Factory;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-03-28 13:53:47
 */
public interface IFactoryService extends IService<Factory> {

	public Page<Factory> findFactoryByPage(Page<Factory> page,Factory factory);
	public Page<Factory> findOrFactoryByPage(Page<Factory> page,String key);
	public List<Factory> findFactoryAll(Factory factory);
	
	
	
}
