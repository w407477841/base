package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Log;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2017-12-14 15:16:13
 */
public interface LogMapper extends BaseMapper<Log> {
	public List<Log> selectLogByPage(Pagination page,@Param("log")Log log);
	public List<Log> selectLogAll(@Param("log")Log log);
	
}
