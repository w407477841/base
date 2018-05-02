package com.zyiot.commonservice.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.zyiot.commonservice.handler.CustomExceptionResolver;
import com.zyiot.commonservice.push.service.IPushService;
import com.zyiot.commonservice.redis.service.IRedisTokenService;
import com.zyiot.commonservice.security.filter.JWTAuthenticationFilter;
import com.zyiot.commonservice.security.filter.JWTLoginFilter;
import com.zyiot.commonservice.security.provider.CustAuthenticationProvider;
import com.zyiot.commonservice.security.provider.UsernameAuthenticationProvider;
import com.zyiot.commonservice.service.impl.UserServiceImpl;


/**
 * 
 *认证配置
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity(debug=true)
@Order(value=SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserServiceImpl userServiceImpl;
	//@Autowired
//	private AuthenticationEntryPoint authenticationEntryPoint;
	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder; 
	@Autowired
	private CustAuthenticationProvider authenticationProvider;
	@Autowired
	private UsernameAuthenticationProvider  usernameAuthenticationProvider;
	@Autowired
	private IPushService pushService;
	
	@Value("${jwt.header}")
	private String header;
	@Value("${jwt.secret}")
	private String secret;
	@Value("${jwt.expiration}")
	private String expiration;
	@Value("${jwt.tokenHead}")
	private String tokenHead;
	
	@Autowired
	private IRedisTokenService  redisTokenService;
	
	





	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		
		auth.authenticationProvider(authenticationProvider).
		authenticationProvider(usernameAuthenticationProvider).
		//authenticationProvider( authenticationProvider()).
		userDetailsService(userServiceImpl).
		passwordEncoder(bCryptPasswordEncoder)
		//.and().authenticationProvider(authenticationProvider)
		;
	}

	
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		 web.ignoring()
         .antMatchers(HttpMethod.POST, "/users")
         .antMatchers("/", "/auth/**", "/resources/**", "/static/**", "/public/**", "/webui/**", "/h2-console/**"
                 , "/configuration/**", "/swagger-ui/**", "/swagger-resources/**", "/api-docs", "/api-docs/**", "/v2/api-docs/**"
                 , "/*.html", "/**/*.html", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg", "/**/*.gif", "/**/*.svg", "/**/*.ico", "/**/*.ttf", "/**/*.woff");

	}

	
	

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		/*
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET,"/admin/").hasAnyRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/user/").hasRole("USER")
		.and().formLogin().loginProcessingUrl("/login/").passwordParameter("password").usernameParameter("username").permitAll()
		.and().csrf().disable().logout().logoutUrl("/logout/").permitAll()
		;
		*/
		 httpSecurity.cors().and()
         // 由于使用的是JWT，我们这里不需要csrf
         .csrf().disable()
         //.exceptionHandling().disable() //去掉异常处理 原本403异常会被处理掉，现在会抛出异常。并且只能在为进入controler的全局异常处理捕获
         // 基于token，所以不需要session
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
         .authorizeRequests()
         // 允许对于网站静态资源的无授权访问
         .antMatchers(
                 HttpMethod.GET,
                 "/",
                 "/*.html",
                 "/favicon.ico",
                 "/**/*.html",
                 "/**/*.css",
                 "/**/*.js"
         ).permitAll()
         .antMatchers(HttpMethod.GET,"/task/**").permitAll()//任务
         .antMatchers(HttpMethod.GET,"/upload/image/**").permitAll()//上传
         .antMatchers(HttpMethod.POST,"/user/signup").permitAll()
         .antMatchers(HttpMethod.GET,"/validRegisterCode").permitAll()//验证注册码
         .antMatchers(HttpMethod.GET,"/phoneCode").permitAll()//获取手机验证码
         .antMatchers(HttpMethod.GET,"/validCode").permitAll()//验证手机号+验证码\
         .antMatchers(HttpMethod.GET,"/loginCode").permitAll()//验证手机登录验证码
         .antMatchers(HttpMethod.POST,"/register").permitAll()//注册
         
         
         .antMatchers(HttpMethod.GET,"/hello/**").permitAll()//websocket
         .antMatchers(HttpMethod.GET,"/send").permitAll()//websocket
         .antMatchers(HttpMethod.GET,"/send/**").permitAll()//websocket
         
         //.antMatchers("/other","/other/**").hasRole("ADMIN")
         // 除上面外的所有请求全部需要鉴权认证
         .anyRequest().authenticated()
         .and()
         .addFilter(new JWTLoginFilter(header, secret, expiration, tokenHead, authenticationManager(),userServiceImpl,redisTokenService,pushService))
         .addFilterAfter(new JWTAuthenticationFilter(header, secret, expiration, tokenHead,userServiceImpl,redisTokenService),JWTLoginFilter.class); 
         
         ;

 // 添加JWT filter
// httpSecurity.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
 // 禁用缓存
 ///httpSecurity.headers().cacheControl();

	}
	
	 /**
     * 进入controller后的异常处理
     * @return
     */
	@Bean
    public CustomExceptionResolver customExceptionResolver() {

  	  return new CustomExceptionResolver();

  	  }
	
/*
	@Bean
	MessageSource getMessageSource(){
	ReloadableResourceBundleMessageSource parentMessageSource = new ReloadableResourceBundleMessageSource();
	parentMessageSource.setDefaultEncoding("UTF-8");
	parentMessageSource.setBasename("classpath:org/springframework/security/messages");
	return parentMessageSource;
	}*/
	/*
	@Bean(name="localeResolver")
	public LocaleResolver localeResolverBean(){
		CookieLocaleResolver localeResolver=new CookieLocaleResolver();
		localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
		return localeResolver;
		
	}
	*/
	
}
