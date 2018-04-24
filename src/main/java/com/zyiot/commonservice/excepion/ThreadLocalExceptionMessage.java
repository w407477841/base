package com.zyiot.commonservice.excepion;

import java.util.HashMap;
import java.util.Map;
/**
 * 存储当前线程 状态码及错误信息
 * 使用push设置,使用pop取出
 *
 */

public class ThreadLocalExceptionMessage {

	public static ThreadLocal<String> message=new ThreadLocal<String>();
	public static ThreadLocal<Integer> status= new ThreadLocal<Integer>();
	
	public static synchronized void  push(String m,Integer s){
		message.set(m);
		status.set(s);
	}
	public static  synchronized Map<String, Object>  pop(){
		String msg=message.get();
		Integer s=status.get();
		message.remove();
		message.remove();
		Map<String, Object>  map=null;
		if(msg!=null&&s!=null){
			map=new HashMap<String, Object>();
			map.put("msg", msg);
			map.put("status",s);
		}
		return map;
	}
}

