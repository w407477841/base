package com.zyiot.commonservice.plc.driver;

import java.net.Socket;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.plc.thread.ReceiveThread;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;
public abstract class AbstractPLCDriver  {
	private IMessageQueueService messageQueueService;
	
	private static Vector<Socket>  sockets=new Vector<Socket>();
	public void setMessageQueueService(IMessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}

	public abstract void send(String order);

	public void add(Socket socket) {
		sockets.add(socket);
		receive(socket);
	}

	public void receive(Socket socket) {
		
		if(socket!=null){
			Thread thread=new Thread(new ReceiveThread(socket,messageQueueService));
			thread.start();
		}
		
	}

	
	
	public abstract String sendAndReceive(String order);

}
