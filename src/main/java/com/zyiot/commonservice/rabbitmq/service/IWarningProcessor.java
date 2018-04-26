package com.zyiot.commonservice.rabbitmq.service;

/**
 * 
 * @author Administrator
 * 消息队列处理 接口
 */
public interface IWarningProcessor {

    public void process(String content) ;
	
}
