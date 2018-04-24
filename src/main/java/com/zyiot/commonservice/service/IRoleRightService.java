package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.RoleRight;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-13 13:36:01
 */
public interface IRoleRightService extends IService<RoleRight> {

	public Page<RoleRight> findRoleRightByPage(Page<RoleRight> page,RoleRight roleRight);
	
	public List<RoleRight> findRoleRightAll(RoleRight roleRight);

	boolean perms(List<RoleRight> roleRights);
	
	
}
