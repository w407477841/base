package com.zyiot.commonservice.plc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.annotation.PostConstruct;

import com.zyiot.commonservice.plc.driver.AbstractPLCDriver;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;

public class PLCServerSocketFactory {

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
	
	public AbstractPLCDriver getDriver() {
		return driver;
	}
	public void setDriver(AbstractPLCDriver driver) {
		this.driver = driver;
	}

	private boolean openClient ;
	private  int    localPort;
	private AbstractPLCDriver  driver;
	
	@PostConstruct
	public void init(){
		if(openClient){//PLC 推送
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
					ServerSocket sc=new ServerSocket(localPort);
					
					while(true){
						Socket socket=sc.accept();
						driver.add(socket);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
					
				}
			}).start();
			
		}else{//PLC做服务端
			
			//开启 一个定时任务
			
			
		}
		
	}
	
}
