package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * 注册使用数据库中存在用户名和手机号相同的情况
 *
 */

public class UsernameEqPhoneException extends UsernameNotFoundException{
	private static final long serialVersionUID = 1L;


	public UsernameEqPhoneException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsernameEqPhoneException(String message) {
		super(message);
	}

	

}
