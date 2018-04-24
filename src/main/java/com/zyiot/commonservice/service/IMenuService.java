package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Menu;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-03-15 09:10:10
 */
public interface IMenuService extends IService<Menu> {

	public Page<Menu> findMenuByPage(Page<Menu> page,Menu menu);
	public Page<Menu> findOrMenuByPage(Page<Menu> page,String key);
	public List<Menu> findMenuAll(Menu menu);
	public List<Menu> findRoleAllSort(String username);
	
	
}
