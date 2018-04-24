package com.zyiot.commonservice.service;

public interface IAliyunMesageService {

	public void sendMessage(String signName,String templateCode,String phoneNumbers,String json_params);
	public String getCode(String signName,String templateCode,String phoneNumber);
	
}
