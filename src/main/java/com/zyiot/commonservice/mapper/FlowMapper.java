package com.zyiot.commonservice.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Job;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-15 09:07:58
 */
public interface FlowMapper{
	
	
	
	@Select("select * from ${tableName} where id = #{id}")
	public Map<String, Object> selectObj(@Param("tableName")String tableName,@Param("id")Integer id);
	
}
