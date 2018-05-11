package com.zyiot.commonservice.plc.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

import com.zyiot.commonservice.rabbitmq.MQConstant;
import com.zyiot.commonservice.rabbitmq.service.IMessageQueueService;


public class ReceiveThread implements Runnable {
	public ReceiveThread(Socket socket,IMessageQueueService messageQueueService) {
		super();
		this.socket = socket;
		this.messageQueueService=messageQueueService;
	}
	private Socket socket;
	private InputStream is;
	private IMessageQueueService messageQueueService;
	
	public IMessageQueueService getMessageQueueService() {
		return messageQueueService;
	}
	public void setMessageQueueService(IMessageQueueService messageQueueService) {
		this.messageQueueService = messageQueueService;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	@Override
	public void run() {
		boolean isStop=false;
		
		try {
			is=socket.getInputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
			isStop=true;
		}
		
		while(!isStop){
             System.out.println("------------------------");
             try {
			//	int count= is.available();
				byte[] bys=new byte[10];
				is.read(bys);
				
				String hex=bytesToHexString(bys);
				//加入 消息队列
				messageQueueService.send(MQConstant.WARNING_ROUTING_KEY, hex);
				
				if(hex.equals("00000000000000000000")){
					System.out.println("已断开");
					isStop=true;
				}
			} catch (IOException e) {
				e.printStackTrace();
				isStop=true;
			}
		}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}	
		
	}
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}
}
