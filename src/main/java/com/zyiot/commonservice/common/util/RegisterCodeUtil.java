package com.zyiot.commonservice.common.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.jayway.jsonpath.internal.function.numeric.Max;

@Component("registerCodeUtil")
public class RegisterCodeUtil {
@Value("${util.secrity.key}")
	private  String secrity=new String("SHA");


public String encode(String rawPassword) {
	  BigInteger sha =null;
    //  System.out.println("=======加密前的数据:"+rawPassword);
      byte[] inputData = rawPassword.getBytes(); 
      String result=null;
      try {
           MessageDigest messageDigest = MessageDigest.getInstance(secrity);  
           messageDigest.update(inputData);
           sha = new BigInteger(messageDigest.digest());   
            result=  sha.toString(32);   
           result=result.substring(result.length()-10);
      } catch (Exception e) {e.printStackTrace();}
      return result;
}

public static void main(String[] args) {

	BlockingQueue<String> queue=new ArrayBlockingQueue<String>(10);
	Thread t1=new Thread(new Runnable() {
		
		@Override
		public void run() {
			for(int i=0;i<10000;i++){
			try {
				queue.put("A="+i);
				System.out.println("生产了"+"A="+i);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep((long) (1000*Math.random()));
			} catch (InterruptedException e) {
			}
			}
		}
	});
	
	Thread t2=new Thread(new Runnable() {
		
		@Override
		public void run() {
			for(int i=0;i<10000;i++){
			try {
				queue.put("B="+i);
				System.out.println("生产了"+"B="+i);
			} catch (InterruptedException e1) {
			}
			try {
				Thread.sleep((long) (1000*Math.random()));
			} catch (InterruptedException e) {
			}
			}
		}
	});
	
Thread t3=new Thread(new Runnable() {
		
		@Override
		public void run() {
			for(int i=0;i<20000;i++){
			try {
				System.out.println("消费了:"+queue.take());
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Thread.sleep((long) (1000*Math.random()));
			} catch (InterruptedException e) {
			}
			}
		}
	});
	t1.start();
	t2.start();
	t3.start();
}
	



}
