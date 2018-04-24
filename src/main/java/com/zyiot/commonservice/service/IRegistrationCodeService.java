package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.RegistrationCode;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-03-29 14:33:27
 */
public interface IRegistrationCodeService extends IService<RegistrationCode> {

	public Page<RegistrationCode> findRegistrationCodeByPage(Page<RegistrationCode> page,RegistrationCode registrationCode);
	public Page<RegistrationCode> findOrRegistrationCodeByPage(Page<RegistrationCode> page,String key);
	public List<RegistrationCode> findRegistrationCodeAll(RegistrationCode registrationCode);
	List<RegistrationCode> insert(Long factory, int count);
	
	
}
