package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
/**
 * 验证码错误
 * @author Administrator
 *
 */
public class CodeErrorException extends UsernameNotFoundException{
	private static final long serialVersionUID = 1L;

	public CodeErrorException(String msg, Throwable t) {
		super(msg, t);
	}

	public CodeErrorException(String msg) {
		super(msg);
	}

}
