package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.ShangjiInfo;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-03-06 15:46:30
 */
public interface ShangjiInfoMapper extends BaseMapper<ShangjiInfo> {
	public List<ShangjiInfo> selectShangjiInfoByPage(Pagination page,@Param("shangjiInfo")ShangjiInfo shangjiInfo);
	public List<ShangjiInfo> selectShangjiInfoAll(@Param("shangjiInfo")ShangjiInfo shangjiInfo);
	
}
