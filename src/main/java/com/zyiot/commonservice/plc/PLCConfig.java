package com.zyiot.commonservice.plc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;
import javax.annotation.processing.Processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zyiot.commonservice.plc.driver.AbstractPLCDriver;
import com.zyiot.commonservice.plc.impl.BaseConectionClient;
import com.zyiot.commonservice.plc.processor.PLCProcessor;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
import com.zyiot.commonservice.redis.service.IRedisDataKeyService;
import com.zyiot.commonservice.redis.service.impl.RedisTest;
import com.zyiot.commonservice.service.IInstrumentDataService;

@Configuration
@ConfigurationProperties(prefix="plc")
public class PLCConfig {
	/**
	 * plc 是否为 客户端模式，即PLC做主站，主动推送数据
	 *  
	 */
	private boolean openClient ;
	
	
	/**
	 * PLC为客户端模式,需要配置，供PLC连接
	 */
	private int localPort;
	private int keyLength;
	private int moduleLength;
	private int dataLength;
	private String end ;
	@Autowired
	private IInstrumentDataService  instrumentDataService;
	@Autowired
private RedisTest redisTest;
	public int getKeyLength() {
		return keyLength;
	}


	public void setKeyLength(int keyLength) {
		this.keyLength = keyLength;
	}


	public int getModuleLength() {
		return moduleLength;
	}


	public void setModuleLength(int moduleLength) {
		this.moduleLength = moduleLength;
	}


	public int getDataLength() {
		return dataLength;
	}


	public void setDataLength(int dataLength) {
		this.dataLength = dataLength;
	}


	public String getEnd() {
		return end;
	}


	public void setEnd(String end) {
		this.end = end;
	}


	public boolean isOpenClient() {
		return openClient;
	}


	public void setOpenClient(boolean openClient) {
		this.openClient = openClient;
	}


	public int getLocalPort() {
		return localPort;
	}


	public void setLocalPort(int localPort) {
		this.localPort = localPort;
	}
	@Autowired
	private IMessageQueueService  messageQueueService;
	@Autowired
	private IRedisDataKeyService  redisDataKeyService;
	
	@Bean()
	/**
	 * 接收驱动
	 * @return
	 */
	public AbstractPLCDriver driver(){
		AbstractPLCDriver driver=	new BaseConectionClient();
		driver.setMessageQueueService(messageQueueService);
		return driver;
	} 
	/**
	 * 数据处理器
	 * @return
	 */
	@Bean
	public PLCProcessor plcprocessor(){
		
		PLCProcessor plcProcessor=new PLCProcessor();
		
		plcProcessor.setDataLength(dataLength);
		plcProcessor.setEnd(end);
		plcProcessor.setKeyLength(keyLength);
		plcProcessor.setModuleLength(moduleLength);
		plcProcessor.setRedisDataKeyService(redisDataKeyService);
		plcProcessor.setInstrumentDataService(instrumentDataService);
		plcProcessor.setRedisTest(redisTest);
		
		return plcProcessor;
		
	}
	
	@Bean()
	public PLCServerSocketFactory serverSocketFactory(){
		PLCServerSocketFactory plcssf=new PLCServerSocketFactory();
		plcssf.setDriver(driver());
		plcssf.setLocalPort(localPort);
		plcssf.setOpenClient(openClient);
		
		return plcssf;
	}
	
	
}
