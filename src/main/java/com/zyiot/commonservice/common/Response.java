package com.zyiot.commonservice.common;

public class Response {

	private int status;
	private String msg;
	private Object data;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	/**
	 * 返回成功
	 * @param data
	 * @return
	 */
	public static Response successRes(Object data){
		
		 Response res=  new Response();
		 res.setStatus(0);
		 res.setData(data);
		
		 return res;
		 
	} 
	/**
	 * 返回错误
	 * @param status
	 * @param msg
	 * @return
	 */
	public static Response errorRes(int status,String msg){
		
		 Response res=  new Response();
		 res.setStatus(status);
		 res.setMsg(msg);
		
		 return res;
		 
	} 
	
	
}
