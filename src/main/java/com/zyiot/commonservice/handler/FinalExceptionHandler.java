package com.zyiot.commonservice.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zyiot.commonservice.excepion.ThreadLocalExceptionMessage;
/**
 * 全局异常处理
 * 未进入controller 如401、404、403
 * 注意 /login 是 通过security处理的，所以出现异常会在这边拦截
 */

@RestController
public class FinalExceptionHandler implements ErrorController {

	@Override
	public String getErrorPath() {
		return "/error";
	}
	 @RequestMapping(value = "/error")
	    public Object error(HttpServletResponse resp, HttpServletRequest req) {
		//获取当前线程的错误信息
		 Map<String, Object> map = ThreadLocalExceptionMessage.pop();
		 if(map==null){
			 map=new HashMap<String, Object>();
			 map.put("status",resp.getStatus());
			 map.put("msg", ErrorCode.msg(resp.getStatus()));
		 }else{
			 resp.setStatus((int)map.get("status"));
		 }
		 return map;
	    }

}
