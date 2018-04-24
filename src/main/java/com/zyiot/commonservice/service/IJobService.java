package com.zyiot.commonservice.service;

import java.io.Serializable;
import java.util.List;

import org.quartz.SchedulerException;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.zyiot.commonservice.entity.Job;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 王一飞
 * @since 2017-12-15 09:07:58
 */
public interface IJobService extends IService<Job> {

	public Page<Job> findJobByPage(Page<Job> page,Job job);
	
	public List<Job> findJobAll(Job job);
	public void addJob(String name,String group ,String cron) throws SchedulerException ;
	public boolean newJob (Job job) throws SchedulerException ;
	public boolean deleteJob (Serializable id) throws SchedulerException ;
	public boolean rescheduleJob (Job job) throws SchedulerException ;
	public boolean pauseJob (Serializable id) throws SchedulerException ;
	public boolean resumeJob (Serializable id) throws SchedulerException ;
	
	
	
	
}
