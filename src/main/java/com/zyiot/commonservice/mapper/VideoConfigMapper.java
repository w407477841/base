package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.VideoConfig;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-05-03 13:56:11
 */
public interface VideoConfigMapper extends BaseMapper<VideoConfig> {
	public List<VideoConfig> selectVideoConfigByPage(Pagination page,@Param("videoConfig")VideoConfig videoConfig);
	public List<VideoConfig> selectOrVideoConfigByPage(Pagination page,@Param("key")String key);
	public List<VideoConfig> selectVideoConfigAll(@Param("videoConfig")VideoConfig videoConfig);
	
}
