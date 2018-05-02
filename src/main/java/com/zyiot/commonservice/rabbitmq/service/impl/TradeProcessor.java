package com.zyiot.commonservice.rabbitmq.service.impl;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.entity.DLXMessage;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
import com.zyiot.commonservice.rabbitmq.service.ITradeProcessor;
/**
 * 死信转发处理器
 * @author Administrator
 *
 */
@Component
@RabbitListener(queues=MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
public class TradeProcessor implements ITradeProcessor {
	@Autowired
	private IMessageQueueService  messageQueueService;
	
	@Override
	@RabbitHandler
	public void process(String content) {
			DLXMessage dlxMessage=new Gson().fromJson(content, DLXMessage.class);
			messageQueueService.send(dlxMessage.getQueueName(), dlxMessage.getContent());
	}

}
