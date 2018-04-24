package com.zyiot.commonservice.plc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.zyiot.commonservice.plc.impl.ClientPLCDriver;
@Component("plcControll")
public class PLCControl {
	public static Vector<Socket> sockets =new Vector<Socket>();
	private ServerSocket sc=null;
	@Value(value="${plc.port}")
	private String port ;
	@PostConstruct
	public void init() {
		try {
			 sc=new ServerSocket(Integer.parseInt(port));
			 Thread thread=new Thread(new AcceptThread());
			 thread.start();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
class	AcceptThread implements Runnable {
		public void run() {
			while (true) {
				System.out.println("===等待PLC接入===");
				Socket socket;
				try {
					socket = sc.accept();
					System.out.println("===接入一个PLC===");
					System.out.println("===获取设备信息===");
					ClientPLCDriver plc=new ClientPLCDriver();
					plc.setSocket(socket);
	      			System.out.println("==打印信息=="+plc.sendAndReceive("ffff"));
	      			sockets.add(socket);
	      			System.out.println("===PLC已加入池中===");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}
} 
	
}
