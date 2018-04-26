package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;

public class PasswordErrorException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public PasswordErrorException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}

}
