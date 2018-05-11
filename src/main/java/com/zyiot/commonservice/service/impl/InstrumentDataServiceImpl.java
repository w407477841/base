package com.zyiot.commonservice.service.impl;

import java.util.List;

import com.zyiot.commonservice.entity.InstrumentData;
import com.zyiot.commonservice.mapper.InstrumentDataMapper;
import com.zyiot.commonservice.service.IInstrumentDataService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2018-05-09 13:34:25
 */
@Service
public class InstrumentDataServiceImpl extends ServiceImpl<InstrumentDataMapper, InstrumentData> implements IInstrumentDataService {

	@Override
	public Page<InstrumentData> findInstrumentDataByPage(Page<InstrumentData> page, InstrumentData instrumentData) {
		return  page.setRecords(baseMapper.selectInstrumentDataByPage(page, instrumentData));
	}
	
	@Override
	public Page<InstrumentData> findOrInstrumentDataByPage(Page<InstrumentData> page, String key) {
		return  page.setRecords(baseMapper.selectOrInstrumentDataByPage(page, key));
	}
	
	@Override
	public List<InstrumentData> findInstrumentDataAll(InstrumentData instrumentData) {
		return baseMapper.selectInstrumentDataAll(instrumentData);
	}

}