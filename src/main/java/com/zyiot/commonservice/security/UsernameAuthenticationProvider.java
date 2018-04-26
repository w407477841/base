package com.zyiot.commonservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.excepion.ThreadLocalExceptionMessage;
import com.zyiot.commonservice.excepion.user.PasswordErrorException;
import com.zyiot.commonservice.service.impl.UserServiceImpl;
/**
 * 自定义用户名密码认证
 */
@Component
public class UsernameAuthenticationProvider implements AuthenticationProvider {

	 @Autowired
	 UserServiceImpl userDetailsService; //主要用来检查用户名

	    @Autowired
	    BCryptPasswordEncoder passwordEncoder; //主要用来比对密码
	
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		
		String username=authentication.getName();
		String password =(String) authentication.getCredentials();
		
		UserDetails user = userDetailsService.loadUserByUsername(username);
		
		if(passwordEncoder.matches(password,user.getPassword())){
			return new MyUsernameAuthenticationToken(user, password, user.getAuthorities());
		}else{
			ThreadLocalExceptionMessage.push("密码错误",401);
			throw new PasswordErrorException("密码错误");	
		}
		
		
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return MyUsernameAuthenticationToken.class.isAssignableFrom(authentication);
	}

}
