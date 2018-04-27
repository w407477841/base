package com.zyiot.commonservice.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.common.util.StringUtil;
import com.zyiot.commonservice.excepion.ThreadLocalExceptionMessage;
import com.zyiot.commonservice.excepion.user.CodeErrorException;
import com.zyiot.commonservice.redis.service.IRedisCodeService;
import com.zyiot.commonservice.redis.service.impl.RedisCodeService;
import com.zyiot.commonservice.security.token.PhoneCodeAuthenticationToken;
import com.zyiot.commonservice.service.impl.UserServiceImpl;
/**
 * 自定义认证类，认证手机验证码,
 * 与PhoneCodeAuthenticationToken绑定
 * @author Administrator
 *
 */
@Component
public class CustAuthenticationProvider implements AuthenticationProvider {

	 @Autowired
	 UserServiceImpl userDetailsService; //主要用来检查用户名

	    @Autowired
	    BCryptPasswordEncoder passwordEncoder; //主要用来比对密码
	    @Autowired
	    IRedisCodeService   redisCodeService;// 验证码缓存
	    
	
	@Override
	public Authentication authenticate(Authentication authentication)
			throws AuthenticationException {
		//  手机
        String phone = authentication.getName();
        // 验证码
        String password = (String) authentication.getCredentials();
		
			UserDetails user = userDetailsService.loadUserByPhone(phone);
			String code= redisCodeService.getCode(RedisCodeService.LOGIN, phone);
			if(StringUtil.isEq(password, code)){
				redisCodeService.clearCode(RedisCodeService.LOGIN, phone);
			return new UsernamePasswordAuthenticationToken(user,password,user.getAuthorities());
			}
			else{
				ThreadLocalExceptionMessage.push("验证码错误",401);
			throw	new CodeErrorException("code error");
			}
        
	}

	@Override
	public boolean supports(Class<?> authentication) {
		/**
		 * 是否是AuthenticationManager支持的
		 */
		return PhoneCodeAuthenticationToken.class.isAssignableFrom(authentication);
	}

}

