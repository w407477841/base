package com.zyiot.commonservice.mapper;

import java.util.List;

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
public interface JobMapper extends BaseMapper<Job> {
	public List<Job> selectJobByPage(Pagination page,@Param("job")Job job);
	public List<Job> selectJobAll(@Param("job")Job job);
	public Job selectJob(@Param("name")String jobName,@Param("group")String jobGroup);
	
}
