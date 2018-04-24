package com.zyiot.commonservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
@Configuration
@EnableSwagger2
public class Swagger2Config {

	 @Bean
	    public Docket createRestApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .apiInfo(apiInfo())
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.zyiot.commonservice.controller"))
	                .paths(PathSelectors.any())
	                .build();
	    }

	    private ApiInfo apiInfo() {
	        return new ApiInfoBuilder()
	                .title("基础 REST API ")
	                .description("需要登录认证，POST login  {\"username\":\"\",\"password\":\"\"}  ,登录成功后会返回 access_token和响应头中带上Authorization,请在所有请求中加入access_token/请求头中添加  Authorization")
	               // .termsOfServiceUrl("http://blog.didispace.com/")
	                .contact("王一飞")
	                .version("1.0")
	                
	                .termsOfServiceUrl("http://192.168.0.166:8090/")
	                .build();
	    }

	
}
