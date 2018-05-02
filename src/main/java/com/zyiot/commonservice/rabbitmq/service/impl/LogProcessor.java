package com.zyiot.commonservice.rabbitmq.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.zyiot.commonservice.entity.Log;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.service.ILogProcessor;
import com.zyiot.commonservice.service.ILogService;
@RabbitListener(queues=MQConstant.LOG_QUEUE_NAME)
@Component
/**
 * 
 * @author Administrator
 * 日志 消息 队列处理器
 * 
 * 处理LogAspect中加入到队列的日志信息
 * 
 */

public class LogProcessor implements ILogProcessor {
	@Autowired
	private ILogService  logService;
	
	@Override
	@RabbitHandler()
	public void process(String content) {
		
		try {
			logService.insert(new Gson().fromJson(content, Log.class));
		} catch (JsonSyntaxException e) {
			throw new RuntimeException("json转换错误");
		}catch (Exception e) {
			throw new RuntimeException("日志添加失败" +e.getMessage());
		}
		
	}

}
