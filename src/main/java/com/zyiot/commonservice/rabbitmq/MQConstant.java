package com.zyiot.commonservice.rabbitmq;

/**
 * 
 * @author Administrator
 *	相关常量
 */

public final class MQConstant {

	private MQConstant(){  
	    }  
	      
	    //exchange name  
	    public static final String DEFAULT_EXCHANGE = "JY";  
	      
	    //DLX QUEUE  
	    public static final String DEFAULT_DEAD_LETTER_QUEUE_NAME = "jy.dead.letter.queue";  
	      
	    //DLX repeat QUEUE 死信转发队列  
	    public static final String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "jy.repeat.trade.queue";  
	      
	      
	    //报警通知队列名称  
	    public static final String WARNING_QUEUE_NAME = "queue.warning"; 
	    
	    // 消息路由 ，用于匹配队列 发送时使用
	    public static final String WARNING_ROUTING_KEY = "warning";
	    
	    public static final String LOG_QUEUE_NAME = "queue.log";
	    
	    public static final String LOG_ROUTING_KEY = "log";
	    
	    
	
}
