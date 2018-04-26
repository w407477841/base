package com.zyiot.commonservice.rabbitmq.service;

public interface IMessageQueueService {

	   /** 
     * 发送消息到队列 
     * @param queue 队列名称 
     * @param message 消息内容 
     */  
    public void send(String queueName,String message); 
    /**
     * 发送延时队列
     * @param queueName
     * @param message
     * @param times
     */
    public void send(String queueName,String message,Long times) ;
	
}
