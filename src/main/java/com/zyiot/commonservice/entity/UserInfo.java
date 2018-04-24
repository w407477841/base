package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2018-04-13
 */
@TableName("t_user_info")
public class UserInfo extends Model<UserInfo> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	private String username;
	private String realname;
    /**
     * 工厂
     */
	@TableField("factory_id")
	private Long factoryId;
    /**
     * 性别 男/女
     */
	private String sex;
    /**
     * 手机号
     */
	private String phone;
    /**
     * 生日 格式yyyy-MM-dd
     */
	private String birth;
    /**
     * 人员类型: 工厂用户/企业用户
     */
	private String type;
    /**
     * 电子信箱
     */
	private String email;
    /**
     * 部门,企业用户
     */
	private Long dept;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Long getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Long factoryId) {
		this.factoryId = factoryId;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDept() {
		return dept;
	}

	public void setDept(Long dept) {
		this.dept = dept;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "UserInfo{" +
			"id=" + id +
			", username=" + username +
			", realname=" + realname +
			", factoryId=" + factoryId +
			", sex=" + sex +
			", phone=" + phone +
			", birth=" + birth +
			", type=" + type +
			", email=" + email +
			", dept=" + dept +
			"}";
	}
}
