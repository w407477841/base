package com.zyiot.commonservice;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.zyiot.commonservice.handler.CustomExceptionResolver;
import com.zyiot.commonservice.job.SpringJobFactory;
//@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
//@EnableWebMvc
public class CommonServiceApplication {
	final String product = "Dysmsapi";//短信API产品名称（短信产品名固定，无需修改）
	final String domain = "dysmsapi.aliyuncs.com";//短信API产品域名（接口地址固定，无需修改）
	
	//替换成你的AK
	@Value("${aliyun.accessKeyId}")
	private String accessKeyId ;//你的accessKeyId,参考本文档步骤2
	@Value("${aliyun.accessKeySecret}")
	private String accessKeySecret ;//你的accessKeySecret，参考本文档步骤2
	
	
	
	//配置 密码器
	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	
	//配置 任务调度相关
	@Autowired
	  private SpringJobFactory springJobFactory;
	 
	  @Bean
	  public SchedulerFactoryBean schedulerFactoryBean() {
	    SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
	    schedulerFactoryBean.setJobFactory(springJobFactory);
	    return schedulerFactoryBean;
	  }
	 
	  @Bean
	  public Scheduler scheduler() {
	    return schedulerFactoryBean().getScheduler();
	  }
	  
	  
	  //配置 跨域相关
	  private CorsConfiguration buildConfig() {  
          CorsConfiguration corsConfiguration = new CorsConfiguration();  
          //corsConfiguration.addAllowedOrigin("http://192.168.0.166:8080");//带token不能用*
          corsConfiguration.addAllowedOrigin("*");
          corsConfiguration.addAllowedHeader("*");  
          corsConfiguration.addAllowedMethod("*");
          corsConfiguration.setAllowCredentials(true);//websocket需要设置
          return corsConfiguration;  
      }  
        
      /** 
       * 跨域过滤器 
       * @return 
       */  
      @Bean  
      public CorsFilter corsFilter() {  
          UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  
          source.registerCorsConfiguration("/**", buildConfig()); // 4  
          return new CorsFilter(source);  
      } 
     
    /**
     * 设置 aliyun短信
     */
	@Bean
	public 	IClientProfile profile(){
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId,
				accessKeySecret);
				try {
					DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
				} catch (ClientException e) {
					throw new RuntimeException(e.getMessage());
				}
		return profile;
	}
	/**
	 * 解决多个bean异常
	 * @return
	 */
	@Primary
	@Bean
	public TaskExecutor primaryTaskExecutor() {
	    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
	    return executor;
	}
	

	public static void main(String[] args) {
		SpringApplication.run(CommonServiceApplication.class, args);
	}
}
