package com.zyiot.commonservice.rabbitmq.service.impl;


import org.json.JSONArray;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





import com.google.gson.Gson;
import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.entity.DLXMessage;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
@Service("messageQueueService")  
public class MessageQueueService implements IMessageQueueService{

	  @Autowired  
	    private RabbitTemplate rabbitTemplate;  
	
	@Override
	public void send(String queueName, String message) {
		 rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE,queueName, message);
	}

	@Override
	public void send(String queueName, String msg, Long times) {
		
		DLXMessage dlxMessage = new DLXMessage(queueName,msg,times);  
		/*
        MessagePostProcessor processor = new MessagePostProcessor(){  
            @Override  
            public Message postProcessMessage(Message message) throws AmqpException {  
                message.getMessageProperties().setExpiration(times + "");  
                return message;  
            }  
        };*/
		
		MessagePostProcessor processor=(message)->{
			message.getMessageProperties().setExpiration(times + "");  
            return message;  
		};
		
		
        dlxMessage.setExchange(MQConstant.DEFAULT_EXCHANGE);  
        rabbitTemplate.convertAndSend(MQConstant.DEFAULT_EXCHANGE,MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME,new Gson().toJson(dlxMessage), processor);  
		
	}

}
