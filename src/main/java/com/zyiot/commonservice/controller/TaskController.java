package com.zyiot.commonservice.controller;

import java.net.Socket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("task")
public class TaskController {
	 @Autowired
	private  BCryptPasswordEncoder passwordEncoder; 
	@GetMapping(value="task1")
	public ResponseEntity<?> task1(@RequestParam("password") String rawPassword){
		String password = "123456";
		System.out.println("正在执行连接检测任务");
			if(passwordEncoder.matches(password, rawPassword)){
				/*
				ClientPLCDriver plc =new ClientPLCDriver();
				synchronized (PLCControl.sockets) {
				System.out.println("当前共有"+PLCControl.sockets.size()+"个PLC");
				for(int i=0;i<PLCControl.sockets.size();i++){
					plc.setSocket(PLCControl.sockets.get(i));
					try {
						plc.send("11");
					} catch (Exception e) {
						System.err.println(e.getMessage());
						PLCControl.sockets.remove(i--);
					}
				}
				System.out.println("连接检测任务执行完毕");
				System.out.println("还有"+PLCControl.sockets.size()+"个PLC存在");
				return new ResponseEntity<String>("success", HttpStatus.OK);
			}	*/
		}
		
		
		System.out.println("task1未执行");
		return new ResponseEntity<String>("密码", HttpStatus.BAD_REQUEST);
}
		
		
	
}
