package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2017-12-15
 */
@TableName(value="t_job")
public class Job extends Model<Job> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 任务名
     */
	@TableField("job_name")
	private String jobName;
    /**
     *  项目.组
     */
	@TableField("job_group")
	private String jobGroup;
    /**
     * url地址
     */
	@TableField("rest_url")
	private String restUrl;
    /**
     * 1删除 0 未删除
     */
	private String deleted;
    /**
     * cron表达式
     */
	private String cron;
    /**
     * 任务密码
     */
	private String password;
	/**
	 * 任务状态 ； 1需要开启  2不需要开启
	 */
	private String status ;
	


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getRestUrl() {
		return restUrl;
	}

	public void setRestUrl(String restUrl) {
		this.restUrl = restUrl;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Job{" +
			"id=" + id +
			", jobName=" + jobName +
			", jobGroup=" + jobGroup +
			", restUrl=" + restUrl +
			", deleted=" + deleted +
			", cron=" + cron +
			", password=" + password +
			"}";
	}
}
