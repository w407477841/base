package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.InstrumentData;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-05-09 13:34:25
 */
public interface InstrumentDataMapper extends BaseMapper<InstrumentData> {
	public List<InstrumentData> selectInstrumentDataByPage(Pagination page,@Param("instrumentData")InstrumentData instrumentData);
	public List<InstrumentData> selectOrInstrumentDataByPage(Pagination page,@Param("key")String key);
	public List<InstrumentData> selectInstrumentDataAll(@Param("instrumentData")InstrumentData instrumentData);
	
}
