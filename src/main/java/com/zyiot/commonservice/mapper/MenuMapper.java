package com.zyiot.commonservice.mapper;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Menu;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-03-15 09:10:10
 */
public interface MenuMapper extends BaseMapper<Menu> {
	public List<Menu> selectMenuByPage(Pagination page,@Param("menu")Menu menu);
	public List<Menu> selectOrMenuByPage(Pagination page,@Param("key")String key);
	public List<Menu> selectMenuAll(@Param("menu")Menu menu);
	@Select("select * from t_menu where id in (select menu_id from t_role_menu where role_id=#{roleId}) and status=1 ")
	public List<Menu> selectMenuAllByRoleId(@Param("roleId")Serializable roleId);
	@Select("select * from t_menu where id in (select menu_id from t_role_menu where role_id in (select role_id from t_user_role where user_id=(select id from t_user where username=#{username})))")
	public List<Menu> selectMenuAllByUsername(@Param("username")String username);
	
}
