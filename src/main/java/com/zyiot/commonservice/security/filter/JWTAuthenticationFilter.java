package com.zyiot.commonservice.security.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.zyiot.commonservice.common.util.StringUtil;
import com.zyiot.commonservice.entity.Right;
import com.zyiot.commonservice.entity.User;
import com.zyiot.commonservice.excepion.ThreadLocalExceptionMessage;
import com.zyiot.commonservice.redis.service.IRedisTokenService;
import com.zyiot.commonservice.service.IUserService;
/**
 * 
 * 拦截需要权限验证的所有请求 
 * 判断 header 中 或  参数中是否有JWT
 *
 */
public class JWTAuthenticationFilter extends OncePerRequestFilter {
	/**
	 * jwt.header = Authorization
jwt.secret = zyiot
jwt.expiration = 604800
jwt.tokenHead = "Bearer " 
	 */
	private String header;
	private String secret;
	private String expiration;
	private String tokenHead;
	private IUserService userService;
	private IRedisTokenService  redisTokenService;
	



	public JWTAuthenticationFilter(String header, String secret,
			String expiration, String tokenHead,IUserService userService,IRedisTokenService  redisTokenService) {
		super();
		this.header = header;
		this.secret = secret;
		this.expiration = expiration;
		this.tokenHead = tokenHead;
		this.userService=userService;
		this.redisTokenService=redisTokenService;
		
	}


	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		 String header = request.getHeader(this.header);  
		 String accessToken=request.getParameter("access_token");
		  
	        if (header == null || !header.startsWith(this.tokenHead)) {
	        	if(accessToken==null ||accessToken.equals("")){
	            chain.doFilter(request, response);  
	            return;  
	        	}
	        	header =this.tokenHead+" " +accessToken;
	        }  
	       //将认证信息放入security上下文
	        SecurityContextHolder.getContext().setAuthentication(getAuthentication(request,header));  
	        
	        
	        chain.doFilter(request, response); 
	}

	
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request,String token) {  
      //  String token = request.getHeader(this.header);  
   
        	//获取IP地址
        	System.out.println("x-forwarded-for:"+request.getHeader("x-forwarded-for"));
        	System.out.println("remoteAddr:"+request.getRemoteAddr());
			if (token != null) {  
			    // 解析 Authorization 得到用户名.  
				Claims claims=	null;
					
						try {
							claims=Jwts.parser()  
										.setSigningKey(this.secret)  
										.parseClaimsJws(token.replace(this.tokenHead+" ", ""))  
										.getBody();
							
						} catch (ExpiredJwtException e) {
						} catch (UnsupportedJwtException e) {
						} catch (MalformedJwtException e) {
						} catch (SignatureException e) {
						} catch (IllegalArgumentException e) {
						} finally{
							if(claims==null){
								//ThreadLocalExceptionMessage.push("登录过期", 403);
								//throw new RuntimeException("登录过期");
								return null;
								
							}
						}
						String user = claims
								.getSubject();
			  /*
			    User u=  userService.loadUserByUsername(user);
			    //判断是否登录
			    if(u!=null&&u.getAccessToken()!=null&&!u.getAccessToken().equals("")&&u.getAccessToken().equals(token.replace(this.tokenHead+" ", ""))){
			    	User principal=new User();
			    	principal.setUsername(user);
				    if (user != null) {  
				        return new UsernamePasswordAuthenticationToken(principal, null,u.getAuthorities());  
				    }  
				    
			    }
			    */
			    //获取缓存
			String  code=  redisTokenService.getCode(user);
			if(code!=null){
				code=this.tokenHead+" "+code;
			}
		//System.err.println("cachetoken="+code);
		//	System.err.println("url  token="+code);
			if(code!=null&&StringUtil.isEq(code, token)){
				User principal=new User();
		    	principal.setUsername(user);
		    	   //解析token中的权限
				  String  authSting= (String) claims.get("auths");
				  authSting= authSting.replace("[", "").replace("]", "").replaceAll(" ", "");
				  String [] authArray=authSting.split(",");
				  
				  Collection<GrantedAuthority> auths =new ArrayList<>();
				  for(String auth:authArray ){
					  auths.add(new SimpleGrantedAuthority(auth));
				  }
				return new UsernamePasswordAuthenticationToken(principal, null,auths);
			}
    }
			return null;
    }
	
}
