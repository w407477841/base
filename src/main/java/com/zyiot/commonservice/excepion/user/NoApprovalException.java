package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 使用 注册未通过的账户登录
 */
public class NoApprovalException extends UsernameNotFoundException{
	private static final long serialVersionUID = 1L;



	public NoApprovalException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoApprovalException(String message) {
		super(message);
	}


}
