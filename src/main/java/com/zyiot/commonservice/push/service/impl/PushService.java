package com.zyiot.commonservice.push.service.impl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnectionManager;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.params.HttpParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.zyiot.commonservice.push.JiGuangPushConfig;
import com.zyiot.commonservice.push.entity.Notify;
import com.zyiot.commonservice.push.service.IPushService;
import com.zyiot.commonservice.websocket.entity.Greeting;
/**
 * 对接极光API
 * @author Administrator
 *
 */
@Component
public class PushService implements IPushService {
	@Autowired
	JiGuangPushConfig  jiGuangPushConfig;
	
	@Autowired  
	private SimpMessageSendingOperations simpMessageSendingOperations; 
	
	
	@Override
	public void push(String message) {

		jiguangPush(message);
		websocketPush(message);
	}

	private void jiguangPush(String message){
		HttpPost httpPost = new HttpPost(jiGuangPushConfig.getUrl());
		 CloseableHttpClient client = HttpClients.createDefault();
		 String respContent = null;
		 Notify notify=new Notify(message);
		 httpPost.addHeader("Authorization", jiGuangPushConfig.getBase64());
		   StringEntity entity = new StringEntity(new Gson().toJson(notify),"utf-8");//解决中文乱码问题    
		   entity.setContentEncoding("UTF-8");    
		   entity.setContentType("application/json");    
		   httpPost.setEntity(entity);
		  
		   try {
			HttpResponse response= client.execute(httpPost);
			if(response.getStatusLine().getStatusCode()==200){
				
				HttpEntity heEntity= response.getEntity();
				
				System.out.println(EntityUtils.toString(heEntity));
			}
			
		} catch (IOException e) {
			
		}
	}
	
	private void websocketPush(String message){
		simpMessageSendingOperations.convertAndSend("/topic/greetings", new Greeting(message,"error","系统"));
	}
	
	public static void main(String[] args) {
		
		new PushService().push("asdasdasd");
	}
}
