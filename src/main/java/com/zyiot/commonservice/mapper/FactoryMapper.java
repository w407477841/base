package com.zyiot.commonservice.mapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Factory;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-03-28 13:53:47
 */
public interface FactoryMapper extends BaseMapper<Factory> {
	public List<Factory> selectFactoryByPage(Pagination page,@Param("factory")Factory factory);
	public List<Factory> selectOrFactoryByPage(Pagination page,@Param("key")String key);
	public List<Factory> selectFactoryAll(@Param("factory")Factory factory);
	public Factory selectById(@Param("id")Serializable id);
	
}
