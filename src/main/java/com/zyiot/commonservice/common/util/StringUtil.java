package com.zyiot.commonservice.common.util;


public class StringUtil {
	public static boolean isEmpty(String s){
		if(s!=null&&!s.trim().equals("")){
			return false;
		}
		return true;
	} 
	/**
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static boolean isEq(String s1,String s2){
		
		if(s1!=null&&s1.equals(s2)){
			return true;
		}else if(s1==null&&s2==null){
			return true;
		}
		
		return false;
	}
	
	public static String randomString(int length){
		StringBuffer sbBuffer=new StringBuffer();
		if(length <=0) throw new RuntimeException("长度要大于0");
		
		for (int i = 0; i < length; i++) {
			sbBuffer.append((int)(Math.random()*10));
		}
		
		return 	sbBuffer.toString();		
	}
	public static void main(String[] args) {
	}
}
