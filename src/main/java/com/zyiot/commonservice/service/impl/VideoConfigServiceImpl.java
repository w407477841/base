package com.zyiot.commonservice.service.impl;

import java.util.List;

import com.zyiot.commonservice.entity.VideoConfig;
import com.zyiot.commonservice.mapper.VideoConfigMapper;
import com.zyiot.commonservice.service.IVideoConfigService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2018-05-03 13:56:11
 */
@Service
public class VideoConfigServiceImpl extends ServiceImpl<VideoConfigMapper, VideoConfig> implements IVideoConfigService {

	@Override
	public Page<VideoConfig> findVideoConfigByPage(Page<VideoConfig> page, VideoConfig videoConfig) {
		return  page.setRecords(baseMapper.selectVideoConfigByPage(page, videoConfig));
	}
	
	@Override
	public Page<VideoConfig> findOrVideoConfigByPage(Page<VideoConfig> page, String key) {
		return  page.setRecords(baseMapper.selectOrVideoConfigByPage(page, key));
	}
	
	@Override
	public List<VideoConfig> findVideoConfigAll(VideoConfig videoConfig) {
		return baseMapper.selectVideoConfigAll(videoConfig);
	}

}