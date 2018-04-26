package com.zyiot.commonservice.rabbitmq.entity;

import java.io.Serializable;
/**
 * 消息载体
 * @author Administrator
 *
 */
public class DLXMessage  implements Serializable{
	private static final long serialVersionUID = 1L;

	public DLXMessage() {  
	        super();  
	    }  
	  
	    public DLXMessage(String queueName, String content, long times) {  
	        super();  
	        this.queueName = queueName;  
	        this.content = content;  
	        this.times = times;  
	    }  
	  
	    public DLXMessage(String exchange, String queueName, String content, long times) {  
	        super();  
	        this.exchange = exchange;  
	        this.queueName = queueName;  
	        this.content = content;  
	        this.times = times;  
	    }  
	  
	  
	    private String exchange;  
	      
	    private String queueName;  
	      
	    private String content;  
	      
	    private long times;

		public String getExchange() {
			return exchange;
		}

		public void setExchange(String exchange) {
			this.exchange = exchange;
		}

		public String getQueueName() {
			return queueName;
		}

		public void setQueueName(String queueName) {
			this.queueName = queueName;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public long getTimes() {
			return times;
		}

		public void setTimes(long times) {
			this.times = times;
		}  
	    
}
