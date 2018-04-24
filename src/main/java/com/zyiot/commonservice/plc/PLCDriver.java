package com.zyiot.commonservice.plc;

import java.net.Socket;

public abstract class PLCDriver {
/**
 * 发送指令并返回结果 
 * @param w 16进制指令
 * @return 16进制数据
 */
	public abstract String  sendAndReceive(String w);
	/**
	 * 发送指令
	 * @param w
	 */
	
	public abstract void  send(String w);
	
}
