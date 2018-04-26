package com.zyiot.commonservice.rabbitmq.service;
/**
 *	死信接收处理 
 */
public interface ITradeProcessor {

	public void process(String content);
	
}
