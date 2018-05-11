package com.zyiot.commonservice.rabbitmq.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.plc.processor.PLCProcessor;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.service.IWarningProcessor;
/**
 * 
 * @author Administrator
 * 消息队列处理类
 *
 */
@Component
@RabbitListener(queues=MQConstant.WARNING_QUEUE_NAME)
public class WarningProcessor implements IWarningProcessor {
	@Autowired
	private PLCProcessor  plcProcessor;
	
	@Override
	@RabbitHandler()
	public void process(String content) {
		plcProcessor.processor(content);
	}
}
