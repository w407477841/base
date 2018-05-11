package com.zyiot.commonservice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.zyiot.commonservice.entity.Equipment;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ${authot}
 * @since 2018-05-08 16:58:14
 */
public interface EquipmentMapper extends BaseMapper<Equipment> {
	public List<Equipment> selectEquipmentByPage(Pagination page,@Param("equipment")Equipment equipment);
	public List<Equipment> selectOrEquipmentByPage(Pagination page,@Param("key")String key);
	public List<Equipment> selectEquipmentAll(@Param("equipment")Equipment equipment);
	
}
