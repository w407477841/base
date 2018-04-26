package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 由于配置表可以更改所有添加了min ，max 
为匹配到的配置表中的数据。
 * </p>
 *
 * @author wyf
 * @since 2018-04-25
 */
@TableName("t_data_warning_info")
public class DataWarningInfo extends Model<DataWarningInfo> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 配置时的最小值
     */
	private BigDecimal min;
	private BigDecimal max;
    /**
     * 数据
     */
	private BigDecimal data;
    /**
     * 执行时间
     */
	@TableField("exec_time")
	private Date execTime;
    /**
     * 内容
     */
	private String content;
    /**
     * 页面通知/短信通知/默认 默认为两种方式
     */
	private String method;
    /**
     * 通知人
     */
	private Long to;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getMin() {
		return min;
	}

	public void setMin(BigDecimal min) {
		this.min = min;
	}

	public BigDecimal getMax() {
		return max;
	}

	public void setMax(BigDecimal max) {
		this.max = max;
	}

	public BigDecimal getData() {
		return data;
	}

	public void setData(BigDecimal data) {
		this.data = data;
	}

	public Date getExecTime() {
		return execTime;
	}

	public void setExecTime(Date execTime) {
		this.execTime = execTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DataWarningInfo{" +
			"id=" + id +
			", min=" + min +
			", max=" + max +
			", data=" + data +
			", execTime=" + execTime +
			", content=" + content +
			", method=" + method +
			", to=" + to +
			"}";
	}
}
