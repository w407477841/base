package com.zyiot.commonservice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.zyiot.commonservice.entity.Right;
import com.zyiot.commonservice.entity.User;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class CommonServiceApplicationTests {

	//@Test
	public void contextLoads() {
		
		
	}
	
	public static void main(String[] args) {
		
		String s=new String(  org.apache.commons.codec.binary.Base64.decodeBase64("eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUxMzIzNTQwNSwiYXV0aHMiOiJbUk9MRV91c2VyX2FkZCwgUk9MRV91c2VyX2RlbGV0ZWQsIFJPTEVfdXNlcl91cGRhdGUsIFJPTEVfdXNlcl9pbmZvLCBST0xFX3VzZXJfcGFnZSwgUk9MRV91c2VyX2FsbCwgUk9MRV9yb2xlX2FkZCwgUk9MRV9yb2xlX2RlbGV0ZWQsIFJPTEVfcm9sZV91cGRhdGUsIFJPTEVfcm9sZV9pbmZvLCBST0xFX3JvbGVfcGFnZSwgUk9MRV9yb2xlX2FsbCwgUk9MRV9yb2xlX3Blcm1zLCBST0xFX3JpZ2h0X2FkZCwgUk9MRV9yaWdodF9kZWxldGVkLCBST0xFX3JpZ2h0X3VwZGF0ZSwgUk9MRV9yaWdodF9pbmZvLCBST0xFX3JpZ2h0X3BhZ2UsIFJPTEVfcmlnaHRfYWxsLCBST0xFX3JvbGVSaWdodF9hZGQsIFJPTEVfcm9sZVJpZ2h0X2RlbGV0ZWQsIFJPTEVfcm9sZVJpZ2h0X3VwZGF0ZSwgUk9MRV9yb2xlUmlnaHRfaW5mbywgUk9MRV9yb2xlUmlnaHRfcGFnZSwgUk9MRV9yb2xlUmlnaHRfYWxsLCBST0xFX2xvZ19hZGQsIFJPTEVfbG9nX2RlbGV0ZWQsIFJPTEVfbG9nX3VwZGF0ZSwgUk9MRV9sb2dfaW5mbywgUk9MRV9sb2dfcGFnZSwgUk9MRV9sb2dfYWxsLCBST0xFX3VzZXJfdXBkcGFzc3dvcmRdIn0"));
		System.out.println(s);
	}

}
