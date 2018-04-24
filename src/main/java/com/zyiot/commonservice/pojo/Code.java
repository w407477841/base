package com.zyiot.commonservice.pojo;

import java.io.Serializable;

public class Code implements Serializable{

	private static final long serialVersionUID = 1L;
	private String code;
	private String message;
	
	
	public Code(String code, String message) {
		this.code = code;
		this.message = message;
	}
	public Code() {
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
