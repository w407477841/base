package com.zyiot.commonservice.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;





import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.redis.service.IRedisCodeService;
import com.zyiot.commonservice.redis.service.IRedisTokenService;
import com.zyiot.commonservice.service.IUserService;
/** 
 * 验证用户名密码正确后，生成一个token，并将token返回给客户端 
 * 该类继承自UsernamePasswordAuthenticationFilter，重写了其中的2个方法 
 * attemptAuthentication ：接收并解析用户凭证。 
 * successfulAuthentication ：用户成功登录后，这个方法会被调用，我们在这个方法里生成token。 
 * 
 * 会拦截
 *  POST /login/
 *  requestBody:{
 *  username:""
 *  password:""
 *  
 *  }
 * 
 */  
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {
	private String header;
	private String secret;
	private String expiration;
	private String tokenHead;
	private AuthenticationManager  authenticationManager;
	private IUserService  userService;
	private IRedisTokenService  redisTokenService;
	public JWTLoginFilter(AuthenticationManager authenticationManager) {
		super();
		
		this.authenticationManager = authenticationManager;
	}
	public JWTLoginFilter(String header, String secret, String expiration,
			String tokenHead,AuthenticationManager authenticationManager,IUserService  userService,IRedisTokenService redisTokenService) {
		super();
		this.header = header;
		this.secret = secret;
		this.expiration = expiration;
		this.tokenHead = tokenHead;
		this.authenticationManager = authenticationManager;
		this.userService  =  userService;
		this.redisTokenService=redisTokenService;
	}


	// 接收并解析用户凭证  
	public Authentication attemptAuthentication(HttpServletRequest req,
			HttpServletResponse res) throws AuthenticationException {
		 try {  
			//获取请求中的用户名/密码
	            User user = new ObjectMapper()  
	                    .readValue(req.getInputStream(), User.class);
	            //通过authenticationManager 验证用户名和密码的有效性
	            Authentication auth=authenticationManager.authenticate(  
	                    new UsernamePasswordAuthenticationToken(  
	                            user.getUsername(),  
	                            user.getPassword(),  
	                            user.getAuthorities()));	
	            
	            return   auth;
	        } catch (IOException e) {  
	            throw new RuntimeException(e);  
	        }  
	}
    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token  
	//返回给前台的数据
	@Override
	protected void successfulAuthentication(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		Claims claims =new  DefaultClaims();
		Long lasttime=System.currentTimeMillis() + Integer.parseInt(expiration);
		claims.put("sub",((User) auth.getPrincipal()).getUsername() );//持有者
		claims.put("exp", new Date(lasttime));//过期时间
		claims.put("auths",Arrays.toString(auth.getAuthorities().toArray()));//权限
		 String token = Jwts.builder()  
	               .setClaims(claims)
	                .setExpiration(new Date(lasttime))  
	                .signWith(SignatureAlgorithm.HS512, this.secret)  
	                .compact(); 
		 /* 更新数据库缓存
	      User user=new User();
	        user.setAccessToken(token);
	        Wrapper<User> wrapper =new EntityWrapper<User>();
	        wrapper.eq("username",((User) auth.getPrincipal()).getUsername() );
	        userService.update(user, wrapper);
	       */ 
	     //更新缓存
		 redisTokenService.updCode(((User) auth.getPrincipal()).getUsername(), token);
		 
		 	res.getWriter().write("{\"status\":\"0\""
		 			+ ","
		 			+ "\"access_token\":\""+token+"\""
		 			+ ","
		 			+ "\"username\":\""+((User) auth.getPrincipal()).getUsername()+"\""
		 			+ "}");
	        res.addHeader(this.header, this.tokenHead+" " + token);  
	        
	  
	        
	        
	}
	
	
	

}
