package com.zyiot.commonservice.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Right;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-13 13:46:24
 */
public interface RightMapper extends BaseMapper<Right> {
	public List<Right> selectRightByPage(Pagination page,@Param("right")Right right);
	public List<Right> selectRightAll(@Param("right")Right right);
	public List<Right> selectRightAllByUser(@Param("userId")Long userId);
	@Select("select * from t_right where id in (select right_id from t_role_right where role_id=#{roleId}) order by module_id")
	public List<Right> selectRightAllByRole(@Param("roleId")Serializable roleId);
	@Select("select * from t_right where module_id=#{moduleId} and id in (select right_id from t_role_right where role_id in (select role_id from t_user_role where user_id=(select id from t_user where username=#{username}))) order by module_id")
	public List<Right> findByModuleAndUsername(@Param("moduleId")Long moduleId, @Param("username")String username);
	
	
}
