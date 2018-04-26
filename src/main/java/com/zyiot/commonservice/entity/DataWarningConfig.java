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
 * 数据报警设置
 * </p>
 *
 * @author wyf
 * @since 2018-04-25
 */
@TableName("t_data_warning_config")
public class DataWarningConfig extends Model<DataWarningConfig> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("equipment_id")
	private Long equipmentId;
	private BigDecimal min;
	private BigDecimal max;
    /**
     * 页面通知/短信通知/默认 默认为两种方式
     */
	private String method;
    /**
     * 启用/禁用
     */
	private String status;
    /**
     * 报警内容
     */
	private String content;
    /**
     * 通知人
     */
	private Long to;
    /**
     * 最近一次执行时间
     */
	@TableField("last_exec")
	private Date lastExec;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEquipmentId() {
		return equipmentId;
	}

	public void setEquipmentId(Long equipmentId) {
		this.equipmentId = equipmentId;
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getTo() {
		return to;
	}

	public void setTo(Long to) {
		this.to = to;
	}

	public Date getLastExec() {
		return lastExec;
	}

	public void setLastExec(Date lastExec) {
		this.lastExec = lastExec;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "DataWarningConfig{" +
			"id=" + id +
			", equipmentId=" + equipmentId +
			", min=" + min +
			", max=" + max +
			", method=" + method +
			", status=" + status +
			", content=" + content +
			", to=" + to +
			", lastExec=" + lastExec +
			"}";
	}
}
