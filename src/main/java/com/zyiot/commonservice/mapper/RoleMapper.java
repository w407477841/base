package com.zyiot.commonservice.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Role;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-13 13:36:00
 */
public interface RoleMapper extends BaseMapper<Role> {
	public List<Role> selectRoleByPage(Pagination page,@Param("role")Role role);
	public List<Role> selectRoleAll(@Param("role")Role role);
	@Select("select * from t_role where id in (select role_id from t_user_role where user_id=#{userId})")
	public List<Role> selectRoleByUserId(@Param("userId") Serializable userId );
	
}
