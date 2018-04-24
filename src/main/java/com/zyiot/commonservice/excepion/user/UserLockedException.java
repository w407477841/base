package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserLockedException extends UsernameNotFoundException{

	private static final long serialVersionUID = 1L;

	public UserLockedException(String msg, Throwable t) {
		super(msg, t);
	}

	public UserLockedException(String msg) {
		super(msg);
	}
	

}
