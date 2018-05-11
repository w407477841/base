package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.InstrumentData;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-05-09 13:34:25
 */
public interface IInstrumentDataService extends IService<InstrumentData> {

	public Page<InstrumentData> findInstrumentDataByPage(Page<InstrumentData> page,InstrumentData instrumentData);
	public Page<InstrumentData> findOrInstrumentDataByPage(Page<InstrumentData> page,String key);
	public List<InstrumentData> findInstrumentDataAll(InstrumentData instrumentData);
	
	
}
