package com.zyiot.commonservice.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
@ControllerAdvice
/**
 * 在执行@RequestMapping时，进入逻辑处理阶段前。譬如传的参数类型错误
 * @author Administrator
 *
 */
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	  @Override
	  protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
		  ex.printStackTrace();
		  Map<String, Object> m= new HashMap<String, Object>();
		  m.put("status", 400);
		  m.put("msg", "缺失参数/参数类型不一致！");
		  ResponseEntity<Object> resp=new ResponseEntity<Object>(m, HttpStatus.BAD_REQUEST);
	        return resp;

	    }
	
}
