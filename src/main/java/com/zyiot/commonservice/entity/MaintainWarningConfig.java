package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2018-04-25
 */
@TableName("t_maintain_warning_config")
public class MaintainWarningConfig extends Model<MaintainWarningConfig> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("equipment_id")
	private Long equipmentId;
    /**
     * 页面通知/短信通知/默认 默认为两种方式
     */
	private String method;
    /**
     * 通知内容
     */
	private String content;
    /**
     * 通知人
     */
	private Long to;
    /**
     * 启用/禁用
     */
	private String status;
    /**
     * 最后执行时间
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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
		return "MaintainWarningConfig{" +
			"id=" + id +
			", equipmentId=" + equipmentId +
			", method=" + method +
			", content=" + content +
			", to=" + to +
			", status=" + status +
			", lastExec=" + lastExec +
			"}";
	}
}
