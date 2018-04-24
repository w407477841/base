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
 * @since 2017-12-08
 */
@TableName(value="t_right")
public class Right extends Model<Right> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 权限名称 -使用访问控制
     */
	@TableField("right_name")
	private String rightName;
    /**
     * 对应资源编号
     */
	@TableField("module_id")
	private Long moduleId;
    /**
     * 可用设备 ***  1代表可用0不可用  web，安卓，ios
     */
	@TableField("available_devices")
	private String availableDevices;
    /**
     * 返回客户端的操作
     */
	private String url;
    /**
     * 返回客户端操作的名称
     */
	@TableField("right_txt")
	private String rightTxt;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRightName() {
		return rightName;
	}

	public void setRightName(String rightName) {
		this.rightName = rightName;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getAvailableDevices() {
		return availableDevices;
	}

	public void setAvailableDevices(String availableDevices) {
		this.availableDevices = availableDevices;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRightTxt() {
		return rightTxt;
	}

	public void setRightTxt(String rightTxt) {
		this.rightTxt = rightTxt;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Right{" +
			"id=" + id +
			", rightName=" + rightName +
			", moduleId=" + moduleId +
			", availableDevices=" + availableDevices +
			", url=" + url +
			", rightTxt=" + rightTxt +
			"}";
	}
}
