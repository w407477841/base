package com.zyiot.commonservice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.pojo.PUserInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-14 08:48:37
 */
public interface UserMapper extends BaseMapper<User> {
	public List<User> selectUserByPage(Pagination page,@Param("user")User user);
	public List<User> selectOrUserByPage(Pagination page,@Param("key")String key);
	public List<User> selectUserAll(@Param("user")User user);
	@Select("select id, realname from t_user where status ='1'")
	public List<Map<String,Object>> selectALlMaps();
	/**
	 * 如果type=username 根据用户名查询
	 * 如果type=phone 根据手机查询
	 * @param username
	 * @param type
	 * @return
	 */
	public User findUserByUsernameOrPhone(@Param("username")String username,@Param("type") String type);
	/**
	 * 查询详细信息，带有phone
	 * @param username
	 * @return
	 */
	public PUserInfo selectUserInfoByUsername(@Param("username")String username); 
	/**
	 * 根据用户手机号查询用户数
	 * @param phone
	 */
	 public Integer selectCountUserByPhone(@Param("phone")String phone);
	
	
}
