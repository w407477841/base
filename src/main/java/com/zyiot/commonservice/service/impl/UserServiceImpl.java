package com.zyiot.commonservice.service.impl;

import java.beans.Beans;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.session.SqlSession;
import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.SqlHelper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.mchange.v2.beans.BeansUtils;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.zyiot.commonservice.annotations.DataWarning;
import com.zyiot.commonservice.entity.RegistrationCode;
import com.zyiot.commonservice.entity.Role;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.entity.UserInfo;
import com.zyiot.commonservice.entity.UserRole;
import com.zyiot.commonservice.excepion.ThreadLocalExceptionMessage;
import com.zyiot.commonservice.excepion.user.ApprovalingException;
import com.zyiot.commonservice.excepion.user.NoApprovalException;
import com.zyiot.commonservice.excepion.user.UserLockedException;
import com.zyiot.commonservice.excepion.user.UsernameEqPhoneException;
import com.zyiot.commonservice.mapper.RegistrationCodeMapper;
import com.zyiot.commonservice.mapper.RightMapper;
import com.zyiot.commonservice.mapper.RoleMapper;
import com.zyiot.commonservice.mapper.UserInfoMapper;
import com.zyiot.commonservice.mapper.UserMapper;
import com.zyiot.commonservice.mapper.UserRoleMapper;
import com.zyiot.commonservice.pojo.PRegisterInfo;
import com.zyiot.commonservice.pojo.PUserInfo;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
import com.zyiot.commonservice.redis.service.IRedisTokenService;
import com.zyiot.commonservice.service.IUserService;

/**
 *
 * @author 王一飞
 * @since 2017-12-14 08:48:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Autowired
	private RightMapper   rightMapper;
	@Autowired
	private RoleMapper   roleMapper;
	@Autowired
	private UserRoleMapper userRoleMapper  ;
	@Autowired
	private PasswordEncoder   bCryptPasswordEncoder;
	@Autowired
	private IRedisTokenService redisTokenService;
	
	@Autowired
	private UserInfoMapper  userInfoMapper;
	@Autowired
	private RegistrationCodeMapper registrationCodeMapper;
	/**
	 * 消息队列发送测试
	 */
	@Autowired
	private IMessageQueueService  messageQueueService;
	
	@Override
	public Page<User> findUserByPage(Page<User> page, User user) {
		page =page.setRecords(baseMapper.selectUserByPage(page, user));
		
		return  page;
	}
	@Override
	public Page<User> findOrUserByPage(Page<User> page, String key) {
		page =page.setRecords(baseMapper.selectOrUserByPage(page, key));
		
		return  page;
	}
	@Override
	public List<User> findUserAll(User user) {
		return baseMapper.selectUserAll(user);
	}
	@Override
	public User loadUserByUsername(String username) {
		User user=null;
			user = baseMapper.findUserByUsernameOrPhone(username,"username");
		/**
		 * 消息队列发送测试
		 
			messageQueueService.send(MQConstant.WARNING_ROUTING_KEY, "nihao",10000L);
		*/
		if(user==null||user.getId()==null){
			ThreadLocalExceptionMessage.push("用户名不存在",401);
			throw new UsernameNotFoundException(username);
		}
		if(user.getApproved().equals("未审核")){
			ThreadLocalExceptionMessage.push("该账户还在审核中",401);
			throw new NoApprovalException("该账户还在审核中");
		}
		if(user.getApproved().equals("不通过")){
			ThreadLocalExceptionMessage.push("该账户未通过审核",401);
			throw new NoApprovalException("该账户未通过审核中");
		}
		if(user.getStatus().equals("0")){
			ThreadLocalExceptionMessage.push("该账户已被禁用，请联系管理员",401);
			throw new UserLockedException("该账户已被禁用，请联系管理员");
		}
		user.setRights(rightMapper.selectRightAllByUser(user.getId()));
		return user;
	}
	@Override
	public User loadUserByPhone(String phone) {
		User user=null;
			user = baseMapper.findUserByUsernameOrPhone(phone,"phone");
		if(user==null||user.getId()==null){
			ThreadLocalExceptionMessage.push("手机号不存在",401);
			throw new UsernameNotFoundException(phone);
		}
		if(user.getApproved().equals("未审核")){
			ThreadLocalExceptionMessage.push("该账户还在审核中",401);
			throw new NoApprovalException("该账户还在审核中");
		}
		if(user.getApproved().equals("不通过")){
			ThreadLocalExceptionMessage.push("该账户未通过审核",401);
			throw new NoApprovalException("该账户未通过审核中");
		}
		if(user.getStatus().equals("0")){
			ThreadLocalExceptionMessage.push("该账户已被禁用，请联系管理员",401);
			throw new UserLockedException("该账户已被禁用，请联系管理员");
		}
		user.setRights(rightMapper.selectRightAllByUser(user.getId()));
		return user;
	}
	@Transactional
	public void updatePassword(User user){
		Wrapper<User> userWrapper=new EntityWrapper<User>();
		userWrapper.eq("username", user.getUsername());
		List<User> users=  baseMapper.selectList(userWrapper);
		User u=users!=null&&users.size()>0? users.get(0):null;
		if(u==null) throw new RuntimeException("未找到用户");
		if(		bCryptPasswordEncoder.matches(user.getOldPassword(),u.getPassword())
){
			u.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		//	u.setAccessToken(UUID.randomUUID().toString());//重置token，需要重新登录
			baseMapper.updateById(u);
			redisTokenService.clearCode(user.getUsername());
		}else{
			throw new RuntimeException("密码错误");
		}
		
	}
	@Transactional(rollbackFor={Exception.class})
	public boolean insert(User entity) {
		boolean insertRet=false;
		try {
			insertRet=retBool(baseMapper.insert(entity));
			
			UserInfo userinfo=new UserInfo();
			BeanUtils.copyProperties(entity, userinfo, "id");
			userInfoMapper.insert(userinfo);
		} catch (DuplicateKeyException e1) {
			throw new RuntimeException("["+entity.getUsername()+"]该用户名已存在");
		}catch (Exception e) {
			throw new RuntimeException("数据库异常");
		}
		if(insertRet){
			//List<Long> roleIds=entity.getRoleIds();
			Long []roleIds=entity.getRoleIds();
			if(roleIds==null){
			throw new IllegalArgumentException("roleIds必须非空");
			}
			List<UserRole> userRoles =new ArrayList<UserRole>();
			for(Long roleId:roleIds){
				UserRole userRole=new UserRole();
				userRole.setRoleId(Long.parseLong(roleId.toString()));
				userRole.setUserId(entity.getId());
				userRoles.add(userRole);
			}
			//userRoleMapper.
			if (CollectionUtils.isEmpty(userRoles)) {
	            throw new IllegalArgumentException("roleIds必须非空");
	        }
	        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(UserRole.class)) {
	            int size = userRoles.size();
	          //  String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
	            String sqlStatement= SqlHelper.table(UserRole.class).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
	            for (int i = 0; i < size; i++) {
	                batchSqlSession.insert(sqlStatement, userRoles.get(i));
	                if (i >= 1 && i % 30 == 0) {
	                    batchSqlSession.flushStatements();
	                }
	            }
	            batchSqlSession.flushStatements();
	        } catch (Throwable e) {
	            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
	        }
	        return true;
		}
		
		 return false;
	}
	@Override
	public User selectById(Serializable id) {
		User user=baseMapper.selectById(id);
		//用户角色
		List<Role> roles= roleMapper.selectRoleByUserId(id);
		Long [] roleIds=new Long[roles.size()];
		for(int i=0;roles!=null&&i<roles.size();i++ ){
			roleIds[i]=roles.get(i).getId();
		}
		//系统所有角色
		Wrapper<Role> rolewrapper=new EntityWrapper<Role>();
		rolewrapper.eq("deleted", "0");
		user.setRoles( roleMapper.selectList(rolewrapper));
		user.setRoleIds(roleIds);
		return user;
	}
@Transactional(rollbackFor={Exception.class})
public boolean updateById(User entity) {
	boolean updateRet=false;
	Long []roleIds=entity.getRoleIds();
	try {
		updateRet=retBool(baseMapper.updateById(entity));
		entity = baseMapper.selectById(entity.getId());
		UserInfo userinfo=new UserInfo();
		BeanUtils.copyProperties(entity, userinfo, "id","username");
		Wrapper<UserInfo> wrapper=new EntityWrapper<UserInfo>();
		wrapper.eq("username",entity.getUsername());
		userinfo.update(wrapper);
	} catch (DuplicateKeyException e1) {
		throw new RuntimeException("["+entity.getUsername()+"]该用户名已存在");
	}catch (Exception e) {
		throw new RuntimeException("数据库异常");
	}
	if(updateRet){
		//List<Long> roleIds=entity.getRoleIds();
		
		//删除原来的用户角色关系 start
		Wrapper<UserRole> delWrapper =new EntityWrapper<UserRole>();
		delWrapper.eq("user_id", entity.getId());
		userRoleMapper.delete(delWrapper);
		//删除原来的用户角色关系 end
		//批量添加用户角色关系  start
		
		if(roleIds==null ){
			throw new IllegalArgumentException("roleIds 不能为空");
		}
		List<UserRole> userRoles =new ArrayList<UserRole>();
		for(Long roleId:roleIds){
			UserRole userRole=new UserRole();
			userRole.setRoleId(Long.parseLong(roleId.toString()));
			userRole.setUserId(entity.getId());
			userRoles.add(userRole);
		}
		//userRoleMapper.
		if (CollectionUtils.isEmpty(userRoles)) {
            throw new IllegalArgumentException("roleIds 不能为空");
        }
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(UserRole.class)) {
            int size = userRoles.size();
          //  String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
            String sqlStatement= SqlHelper.table(UserRole.class).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
            for (int i = 0; i < size; i++) {
                batchSqlSession.insert(sqlStatement, userRoles.get(i));
                if (i >= 1 && i % 30 == 0) {
                    batchSqlSession.flushStatements();
                }
            }
            batchSqlSession.flushStatements();
        } catch (Throwable e) {
            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
        }
        //批量添加用户角色关系  end
        //清除token缓存，重新登录，以防角色变动
        redisTokenService.clearCode(entity.getUsername());
        
        return true;
	}
		return false;
	}
@Override
public PUserInfo selectUserInfoByUsername(String username) {
	return baseMapper.selectUserInfoByUsername(username);
}
@Transactional
public void updateUserInfo(String username,String phone,String sex,String birth,String email) {
	
	Wrapper<UserInfo> wrapper =new EntityWrapper<UserInfo>();
	wrapper.eq("username", username);
	UserInfo userinfo=new UserInfo();
	userinfo.setSex(sex);
	userinfo.setBirth(birth);
	userinfo.setPhone(phone);
	userinfo.setEmail(email);
	if(!retBool(userInfoMapper.update(userinfo, wrapper))){
		throw new RuntimeException("修改失败");
	}
	
}
@Override
public void isExist(String phone) {
	
	Wrapper<UserInfo> wrapper =new EntityWrapper<UserInfo>();
	wrapper.eq("phone", phone);
	Integer count= userInfoMapper.selectCount(wrapper);
	if(count==1){
		throw new RuntimeException("手机号已存在");
	}
	
}

/**
 * 注册
 */
@Override
@Transactional
public void insertRegister(PRegisterInfo registerInfo) {
		
	
		//封装user、userInfo
	    //添加角色 工厂用户
		User user = new User();
		//拷贝到user
		BeanUtils.copyProperties(registerInfo, user);
		user.setApproved("未审核");
		//密码编码
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		try {
			if(!retBool(baseMapper.insert(user))){//user添加失败
				throw new RuntimeException("[user]添加失败");
			}
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("用户名已存在");
		}
		
		//根据 registercode检索 出对应工厂
		RegistrationCode query=new RegistrationCode();
		query.setCode(registerInfo.getRegisterCode());
		RegistrationCode registrationCode= registrationCodeMapper.selectOne(query);
		
		if(registrationCode ==null||registrationCode.getStatus()==null||registrationCode.getStatus().equals("已使用")){
			throw new RuntimeException("注册码已被使用/注册码不存在");
		}
		
		if(!retBool(registrationCodeMapper.changeStatus("已使用", registrationCode.getId()))){
			throw new RuntimeException("注册码已被使用");
		}
		
		UserInfo userInfo =new UserInfo();
		//拷贝到userinfo
		BeanUtils.copyProperties(registerInfo, userInfo);
		//设置工厂ID
		userInfo.setFactoryId(registrationCode.getFactoryId());
		//设个用户类型
		userInfo.setType("工厂用户");
		Wrapper<UserInfo> wrapper=new EntityWrapper<UserInfo>();
		wrapper.eq("username", userInfo.getUsername());
		//插入
		try {
			if(!retBool(userInfoMapper.insert(userInfo))){
				throw new RuntimeException("[userinfo]添加失败");
			}
		} catch (DuplicateKeyException e) {
			throw new RuntimeException("用户名已存在");
		}catch (Exception e) {
			throw new RuntimeException("添加[userinfo]失败");
		}
		
		//查询 工厂用户的角色
			Role roleQuery=new Role();
			roleQuery.setRoleName("factory");
		Role factoryRole=roleMapper.selectOne(roleQuery);
		if(factoryRole==null||factoryRole.getId()==null){
			throw new RuntimeException("缺少factory角色,请联系管理员");
		}
		//添加用户角色关系
		UserRole userRole=new UserRole();
		userRole.setUserId(user.getId());
		userRole.setRoleId(factoryRole.getId());
		boolean isThrow=false;
		try {
			if(!retBool(userRoleMapper.insert(userRole))){
				isThrow=true;
			}
		} catch (Exception e) {
			isThrow=true;
			
		}finally{
			if(isThrow){
				throw new RuntimeException("添加用户角色关系时出错");
			}
		}
		
		
}



}