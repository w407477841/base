package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;

public class ParamsException extends AuthenticationException{
	private static final long serialVersionUID = 1L;

	public ParamsException(String msg) {
		super(msg);
	}

	

}
