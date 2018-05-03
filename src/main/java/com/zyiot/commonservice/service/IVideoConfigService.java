package com.zyiot.commonservice.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zyiot.commonservice.entity.VideoConfig;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2018-05-03 13:56:11
 */
public interface IVideoConfigService extends IService<VideoConfig> {

	public Page<VideoConfig> findVideoConfigByPage(Page<VideoConfig> page,VideoConfig videoConfig);
	public Page<VideoConfig> findOrVideoConfigByPage(Page<VideoConfig> page,String key);
	public List<VideoConfig> findVideoConfigAll(VideoConfig videoConfig);
	
	
}
