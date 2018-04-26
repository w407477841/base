package com.zyiot.commonservice.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * 用于创建队列,延时队列
 * 延时队列原理,创建一个不会被消费的队列，设置超时时间，到达时间后会成为死信，然后会转发到死信转发队列，从而达到模拟的效果
 * @author Administrator
 *
 */
@Configuration
public class QueueConfig {

	private static final Logger log =LoggerFactory.getLogger(QueueConfig.class);
	
	//创建信道
    @Bean  
    public DirectExchange defaultExchange() {  
        return new DirectExchange(MQConstant.DEFAULT_EXCHANGE, true, false);  
    }
    //创建队列
    @Bean
    public Queue queue(){
    	return new Queue(MQConstant.WARNING_QUEUE_NAME, true);
    }
    @Bean
    //将信道与queue.warning队列绑定,匹配warning完全匹配,部分匹配可用#表示
    public Binding binding(){
    	return BindingBuilder.bind(queue()).to(defaultExchange()).with(MQConstant.WARNING_ROUTING_KEY);
    }
    
    /**
     * 死信转发队列
     * @return
     */
    @Bean  
    public Queue repeatTradeQueue() {  
    	return  new Queue(MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME,true,false,false);  
    }  
      /**
       * 绑定
       * @return
       */
    @Bean  
    public Binding  drepeatTradeBinding() {  
        return BindingBuilder.bind(repeatTradeQueue()).to(defaultExchange()).with(MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME);  
    }  
    /**
     * 死信队列
     * @return
     */
    @Bean  
    public Queue deadLetterQueue() {  
    	Map<String,Object> arguments = new HashMap<String,Object>();  
        arguments.put("x-dead-letter-exchange", MQConstant.DEFAULT_EXCHANGE);  
        arguments.put("x-dead-letter-routing-key", MQConstant.DEFAULT_REPEAT_TRADE_QUEUE_NAME);  
        Queue queue = new Queue(MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME,true,false,false,arguments);  
        return queue;   
    }  
  
  /**
   * 绑定
   * @return
   */
    @Bean  
    public Binding  deadLetterBinding() {  
        return BindingBuilder.bind(deadLetterQueue()).to(defaultExchange()).with(MQConstant.DEFAULT_DEAD_LETTER_QUEUE_NAME);  
    }  
    
    
	
}
