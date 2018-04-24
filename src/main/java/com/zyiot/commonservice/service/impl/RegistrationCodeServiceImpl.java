package com.zyiot.commonservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.zyiot.commonservice.common.util.BatchUtil;
import com.zyiot.commonservice.common.util.RegisterCodeUtil;
import com.zyiot.commonservice.entity.RegistrationCode;
import com.zyiot.commonservice.mapper.RegistrationCodeMapper;
import com.zyiot.commonservice.service.IRegistrationCodeService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.bouncycastle.jcajce.provider.asymmetric.rsa.RSAUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 王一飞
 * @since 2018-03-29 14:33:27
 */
@Service
public class RegistrationCodeServiceImpl extends ServiceImpl<RegistrationCodeMapper, RegistrationCode> implements IRegistrationCodeService {
	@Autowired
	RegisterCodeUtil registerCodeUtil;
	@Override
	public Page<RegistrationCode> findRegistrationCodeByPage(Page<RegistrationCode> page, RegistrationCode registrationCode) {
		return  page.setRecords(baseMapper.selectRegistrationCodeByPage(page, registrationCode));
	}
	
	@Override
	public Page<RegistrationCode> findOrRegistrationCodeByPage(Page<RegistrationCode> page, String key) {
		return  page.setRecords(baseMapper.selectOrRegistrationCodeByPage(page, key));
	}
	
	@Override
	public List<RegistrationCode> findRegistrationCodeAll(RegistrationCode registrationCode) {
		return baseMapper.selectRegistrationCodeAll(registrationCode);
	}
	/**
	 * 根据工厂,生成count个注册码
	 */
	@Transactional
	@Override
	public List<RegistrationCode> insert(Long factory,int  count) {
		
		//BatchUtil<RegistrationCode> batchUtil=new BatchUtil<RegistrationCode>();
		List<RegistrationCode> entities =new ArrayList<RegistrationCode>();
		for (int i = 0; i < count; i++) {
			
			RegistrationCode code=new RegistrationCode();
			code.setFactoryId(factory);
			code.setCode(registerCodeUtil.encode(""+factory+"-"+(i+1)));
			code.setStatus("未使用");
			
			try {
				if(retBool(baseMapper.insert(code))){
					entities.add(code);
				}
			} catch (Exception e) {
				}
		}
		
		
		
		return entities;
	}

	
	
}