package com.zyiot.commonservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.IClientProfile;
import com.zyiot.commonservice.common.util.StringUtil;
import com.zyiot.commonservice.entity.enums.AliyunSMS;
import com.zyiot.commonservice.service.IAliyunMesageService;
/**
 * 调用aliyun 短信API
 * @author Administrator
 *
 */
@Service
public class AliyunMesageService implements IAliyunMesageService {
	
	@Autowired
	IClientProfile profile;
	
	
	@Override
	public void sendMessage(String signName,String templateCode,String phoneNumbers,String json_params) {
	
				
				IAcsClient acsClient = new DefaultAcsClient(profile);
				 //组装请求对象
				 SendSmsRequest request = new SendSmsRequest();
				 //使用post提交
				 request.setMethod(MethodType.POST);
				 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
				 request.setPhoneNumbers(phoneNumbers);
				 //必填:短信签名-可在短信控制台中找到
				 request.setSignName(signName);
				 //必填:短信模板-可在短信控制台中找到
				 request.setTemplateCode(templateCode);
				 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
				 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
				 request.setTemplateParam(json_params);
				 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
				 //request.setSmsUpExtendCode("90997");
				 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
				 request.setOutId("yourOutId");
				//请求失败这里会抛ClientException异常
				SendSmsResponse sendSmsResponse=null;
				try {
					sendSmsResponse = acsClient.getAcsResponse(request);
				} catch (ServerException e) {
					
				} catch (ClientException e) {
				}
				if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
				//请求成功
		
	}
	}

	@Override
	public String getCode(String signName, String templateCode, String phoneNumber) {
		IAcsClient acsClient = new DefaultAcsClient(profile);
		 //组装请求对象
		 SendSmsRequest request = new SendSmsRequest();
		 //使用post提交
		 request.setMethod(MethodType.POST);
		 //必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		 request.setPhoneNumbers(phoneNumber);
		 //必填:短信签名-可在短信控制台中找到
		 request.setSignName(signName);
		 //必填:短信模板-可在短信控制台中找到
		 request.setTemplateCode(templateCode);
		 //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		 //友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		 String code =StringUtil.randomString(4); 
		// System.out.println("code="+code);
		 String json_params="{\"code\":\""+code+"\"}";
		 request.setTemplateParam(json_params);
		 //可选-上行短信扩展码(扩展码字段控制在7位或以下，无特殊需求用户请忽略此字段)
		 //request.setSmsUpExtendCode("90997");
		 //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		// request.setOutId("yourOutId");
		//请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse=null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (ServerException e) {
			throw new RuntimeException(e.getMessage());
		} catch (ClientException e) {
			throw new RuntimeException(e.getMessage());
		}
		if(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
		//请求成功
			return code;
		}
		throw new RuntimeException(AliyunSMS.msg(sendSmsResponse.getCode()));
	}
}
