package com.zyiot.commonservice.service;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.Role;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-13 13:36:00
 */
public interface IRoleService extends IService<Role> {

	public Page<Role> findRoleByPage(Page<Role> page,Role role);
	
	public List<Role> findRoleAll(Role role);

	public Role selectRoleRightByid(Serializable id);

	public boolean updateRoleRight(Role role);
	
	
}
