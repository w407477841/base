package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.RoleRight;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-13 13:36:01
 */
public interface RoleRightMapper extends BaseMapper<RoleRight> {
	public List<RoleRight> selectRoleRightByPage(Pagination page,@Param("roleRight")RoleRight roleRight);
	public List<RoleRight> selectRoleRightAll(@Param("roleRight")RoleRight roleRight);
	
}
