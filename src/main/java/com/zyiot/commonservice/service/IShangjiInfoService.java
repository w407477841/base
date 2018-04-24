package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.ShangjiInfo;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-03-06 15:46:30
 */
public interface IShangjiInfoService extends IService<ShangjiInfo> {

	public Page<ShangjiInfo> findShangjiInfoByPage(Page<ShangjiInfo> page,ShangjiInfo shangjiInfo);
	
	public List<ShangjiInfo> findShangjiInfoAll(ShangjiInfo shangjiInfo);
	
	public String flowStartShangji(ShangjiInfo info,String userId, String processDefinitionId);
	
	public boolean flowCompleteShangji(ShangjiInfo info,String taskId); 
	
}
