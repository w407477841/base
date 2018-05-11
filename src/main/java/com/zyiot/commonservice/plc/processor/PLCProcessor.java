package com.zyiot.commonservice.plc.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;

import com.alibaba.druid.sql.visitor.functions.Length;
import com.zyiot.commonservice.entity.Equipment;
import com.zyiot.commonservice.entity.InstrumentData;
import com.zyiot.commonservice.redis.service.IRedisDataKeyService;
import com.zyiot.commonservice.redis.service.impl.RedisTest;
import com.zyiot.commonservice.service.IInstrumentDataService;

/**
 *PLC数据解析器 
 *
 *
 *缺少报警
 *
 *
 */
public class PLCProcessor {
/**
 * 唯一标识长度
 */
	private int keyLength ;
/**
 * 设备模块标识长度	
 */
	private int moduleLength;
/**
* 数据长度	
*/
	private int dataLength;
	/**
	 * 结束符
	 */
	private String end ;
	
	private IRedisDataKeyService  redisDataKeyService;
	
	private IInstrumentDataService  instrumentDataService;
	
	private RedisTest  redisTest;
	
	
	public RedisTest getRedisTest() {
		return redisTest;
	}

	public void setRedisTest(RedisTest redisTest) {
		this.redisTest = redisTest;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public IInstrumentDataService getInstrumentDataService() {
		return instrumentDataService;
	}

	public void setInstrumentDataService(
			IInstrumentDataService instrumentDataService) {
		this.instrumentDataService = instrumentDataService;
	}

	public IRedisDataKeyService getRedisDataKeyService() {
		return redisDataKeyService;
	}

	public void setRedisDataKeyService(IRedisDataKeyService redisDataKeyService) {
		this.redisDataKeyService = redisDataKeyService;
	}

	public int getKeyLength() {
	return keyLength;
	}

public void setKeyLength(int keyLength) {
	this.keyLength = keyLength;
}

public int getModuleLength() {
	return moduleLength;
}


public void setModuleLength(int moduleLength) {
	this.moduleLength = moduleLength;
}


public int getDataLength() {
	return dataLength;
}


public void setDataLength(int dataLength) {
	this.dataLength = dataLength;
}



	private String randomData(){
		
		String [] keys={
				"ABCD","ABCE","ABCF","ABDD","ABDE","ABDF","ABED","ABEE","ABEF","ABFD"
		};
		
		String [] modules={
			   "AA01","AA02","AA03","AA04"	
		};
		
		String [] datas={
			"8080","8090","9080","1010","2030","1020","3020","8070","6070","9080"	
		};
		int random10 =(int) (Math.random()*10);
		int random4 = (int) (Math.random()*4);
		String randomKey=keys[random10];
		String randomModule=modules[random4];
		String randomData=datas[random10];
		
		return randomKey+randomModule+randomData+"FF";
	}
	public void processor(String datas){
		/**
		 *  ABCDAA018080FF
		 *  ABCEAA018081FF
		 *  ABCFAA018080FF
		 */
		
		datas=randomData();
		
		String key = datas.substring(0, keyLength);
		
		//redisTest.test1(key); 测试value相同，key不同   会指向同一个
		//redisTest.test2(key);
		
		//有效数据长度 有效数据为结束符前的长度
		int length =datas.lastIndexOf(end) ;
				//验证数据有效性
				String cacheKey= redisDataKeyService.get(key);
				if(cacheKey!=null){
					//设备有问题，并且在1小时内已经处理过一次
				}else{
					if((length-keyLength)%(moduleLength+dataLength)!=0){//设备配置有问题
						System.out.println("设备有问题");
						redisDataKeyService.update(key);
						return ;
					}else{
						Equipment equipment= redisDataKeyService.getEquipment(key);
						if(equipment==null){//设备没有配置
						redisDataKeyService.update(key);
						return ;
						}
						//解析
						int dataCount = (length-keyLength)/(moduleLength+dataLength);
						//模块标识+数据块
						String moduleAndDatas=datas.substring(keyLength, length) ;
						List<InstrumentData> list=new ArrayList<InstrumentData>();
						for(int i =0 ;i<dataCount ; i++){
							String moduleAndData= moduleAndDatas.substring(i*(moduleLength+dataLength), (i+1)*(moduleLength+dataLength));
							String module = moduleAndDatas.substring(i*(moduleLength+dataLength),i*(moduleLength+dataLength)+moduleLength);
							String data = moduleAndDatas.substring(i*(moduleLength+dataLength)+moduleLength, (i+1)*(moduleLength+dataLength));
							if(equipment.getType().equals("instrument")){
								InstrumentData insData=new InstrumentData();
								insData.setEquipmentId(equipment.getId());
								insData.setModule(module);
								insData.setData(new BigDecimal(Integer.parseInt(data, 16)).divide(new BigDecimal(100)));
								insData.setTime(new Date());
								list.add(insData);
							}
							
						}
						instrumentDataService.insertBatch(list);
					}
					
				}
		
	}
	public static void main(String[] args) {
			//
		
		
	}
	
}
