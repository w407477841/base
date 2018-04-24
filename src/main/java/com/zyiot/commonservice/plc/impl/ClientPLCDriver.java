package com.zyiot.commonservice.plc.impl;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.zyiot.commonservice.plc.PLCDriver;

public class ClientPLCDriver extends PLCDriver{
	private Socket socket;
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	@Override
	public String sendAndReceive(String w) {
		if(socket==null) throw new RuntimeException("连接为空"); 
		InputStream is=null;
		OutputStream os=null;
		try {
			 is=socket.getInputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("InputStream输出流获取失败");
		}
		try {
			os=socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("OutputStream输入流获取失败");
		}
		
		try {
			os.write(hexStringToByte(w));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("指令发送失败");
		}
		byte [] data = new byte[100];
		try {
			is.read(data);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("数据接收失败");
		}
		return bytesToHexString(data);
	}

	@Override
	public void send(String w) {
		if(socket==null) throw new RuntimeException("连接为空"); 
		OutputStream os=null;
		try {
			os=socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("OutputStream输入流获取失败");
		}
		
		try {
			os.write(hexStringToByte(w));
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("指令发送失败");
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

	public static byte[] hexStringToByte(String hex) {
		int len = (hex.length() / 2);
		byte[] result = new byte[len];
		char[] achar = hex.toCharArray();
		for (int i = 0; i < len; i++) {
			int pos = i * 2;
			result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
		}
		return result;
	}

	private static int toByte(char c) {

		byte b = (byte) "0123456789ABCDEF".indexOf(c);
		if (b < 0)
			b = (byte) "0123456789abcdef".indexOf(c);
		return b;
	}

	public static void main(String[] args) {
		try {
		ServerSocket scServerSocket=new ServerSocket(9999);
		ClientPLCDriver plcDriver=new ClientPLCDriver();
		while (true) {
			Socket socket=scServerSocket.accept();
			plcDriver.setSocket(socket);
			System.out.println(plcDriver.sendAndReceive("ff"));
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
