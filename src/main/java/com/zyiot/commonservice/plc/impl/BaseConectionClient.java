package com.zyiot.commonservice.plc.impl;

import com.zyiot.commonservice.plc.driver.AbstractPLCDriver;

public class BaseConectionClient extends AbstractPLCDriver {

	@Override
	public	void send(String order) {//实现发送方法

	}

	@Override
	public String sendAndReceive(String order) {//实现发送并接收，可能会接受不到
		return null;
	}

}
