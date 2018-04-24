package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Right;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-13 13:46:24
 */
public interface IRightService extends IService<Right> {

	public Page<Right> findRightByPage(Page<Right> page,Right right);
	
	public List<Right> findRightAll(Right right);

	public List<Right> findByModuleAndUsername(Long moduleId, String username);
	
	
}
