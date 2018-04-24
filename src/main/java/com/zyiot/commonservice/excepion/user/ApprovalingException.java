package com.zyiot.commonservice.excepion.user;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 使用 注册为审批的账户登录
 * @author Administrator
 *
 */
public class ApprovalingException extends UsernameNotFoundException {
	private static final long serialVersionUID = 1L;


	public ApprovalingException(String msg) {
		super(msg);
	}
	

	public ApprovalingException(String msg, Throwable t) {
		super(msg, t);
	}
}
