package com.zyiot.commonservice.service.impl;

import java.io.Serializable;
import java.util.List;

import com.zyiot.commonservice.entity.Job;
import com.zyiot.commonservice.job.RestJob;
import com.zyiot.commonservice.mapper.JobMapper;
import com.zyiot.commonservice.service.IJobService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;
/**
 *
 * @author 王一飞
 * @since 2017-12-15 09:07:58
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements IJobService {

	@Autowired
private 	Scheduler scheduler;
	
	
	@Override
	public Page<Job> findJobByPage(Page<Job> page, Job job) {
		return  page.setRecords(baseMapper.selectJobByPage(page, job));
	}
	@Override
	public List<Job> findJobAll(Job job) {
		return baseMapper.selectJobAll(job);
	}
	
	public void addJob(String name,String group ,String cron) throws SchedulerException {
		JobDetail job = JobBuilder.newJob(RestJob.class).withIdentity(name, group).build();
		
	       Trigger trg = newTrigger()
                   .withIdentity(name,group)
                   .withSchedule(cronSchedule(cron))
                   .build();  
	       scheduler.scheduleJob(job, trg);
	}
	
	
	 @Transactional(rollbackFor = Exception.class)
	 @Override
	/**
	 * 创建任务
	 */
	
	public boolean newJob(Job entity) throws SchedulerException {
		int row= baseMapper.insert(entity);
		 
		 JobDetail job = JobBuilder.newJob(RestJob.class).withIdentity(entity.getJobName(), entity.getJobGroup()).build();
			if(retBool(row)){
				Trigger trg = newTrigger()
						.withIdentity(entity.getJobName(), entity.getJobGroup())
						.withSchedule(cronSchedule(entity.getCron()))
						.build();  
				scheduler.scheduleJob(job, trg);
			return true;
			}
		 
	        return false;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	/**
	 * 删除任务
	 */
	
	public boolean deleteJob(Serializable id) throws SchedulerException {
			Job job= baseMapper.selectById(id);
				if(job != null ){ 
					if(retBool(baseMapper.deleteById(id))){
					TriggerKey tk = TriggerKey.triggerKey(job.getJobName(),job.getJobGroup());
		            scheduler.pauseTrigger(tk);//停止触发器  
		            scheduler.unscheduleJob(tk);//移除触发器
		            JobKey jobKey = JobKey.jobKey(job.getJobName(),job.getJobGroup());
		            scheduler.deleteJob(jobKey);//删除作业	
					return true;   
					}
				}
				return false;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	/**
	 * 修改任务执行cron
	 */
	public boolean rescheduleJob(Job entity) throws SchedulerException {
		Job old= baseMapper.selectById(entity.getId());
		if(old!=null){
			old.setCron(entity.getCron());
			if(retBool(baseMapper.updateById(old))){
				if(old.getStatus()!=null&&old.getStatus().equals("1")){
				TriggerKey tk = TriggerKey.triggerKey(old.getJobName(), old.getJobGroup());
	            //构造任务触发器
	            Trigger trg = newTrigger()
	                    .withIdentity(old.getJobName(), old.getJobGroup())
	                    .withSchedule(cronSchedule(old.getCron()))
	                    .build();       
	            scheduler.rescheduleJob(tk, trg);
				}
				return true;
			}
		}
		
		return false;
		
	}
	/**
	 * 暂停 任务
	 * 将任务设置为 不开启
	 */
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean pauseJob(Serializable id) throws SchedulerException {
		
			Job old=   baseMapper.selectById(id);
			if(old !=null ){
				old.setStatus("2");
			}
			
			
			if(retBool(baseMapper.updateById(old))){
				JobKey jobKey = JobKey.jobKey(old.getJobName(), old.getJobGroup());
				scheduler.pauseJob(jobKey);
				
				return true;
			}
			
            
            return false;
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean resumeJob(Serializable id) throws SchedulerException {
		Job old=   baseMapper.selectById(id);
		if(old !=null ){
			old.setStatus("1");
		}
		
		
		if(retBool(baseMapper.updateById(old))){
			JobKey jobKey = JobKey.jobKey(old.getJobName(), old.getJobGroup());
			scheduler.resumeJob(jobKey);
			
			return true;
		}
		
        
        return false;
		
	}

}