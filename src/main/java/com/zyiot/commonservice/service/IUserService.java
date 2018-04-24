package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.pojo.PRegisterInfo;
import com.zyiot.commonservice.pojo.PUserInfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-14 08:48:37
 */
public interface IUserService extends IService<User>,UserDetailsService {

	public Page<User> findUserByPage(Page<User> page,User user);
	
	public List<User> findUserAll(User user);
	
	public User loadUserByUsername(String username) throws UsernameNotFoundException;
	
	public void updatePassword(User user);

	Page<User> findOrUserByPage(Page<User> page, String key);
	/**
	 * 查询详细信息，带有phone
	 * @param username
	 * @return
	 */
	public PUserInfo selectUserInfoByUsername(String username);
	
	/**
	 *修改  手机、性别、生日
	 */
	
	public void updateUserInfo(String username,String phone,String sex,String birth,String email);
	/**
	 * 手机号是否存在
	 * @param phone
	 */
	public void isExist(String phone);
	/**
	 * 注册用户
	 * @param registerInfo
	 */
	public void insertRegister(PRegisterInfo registerInfo );

	User loadUserByPhone(String phone);
	
	
}
