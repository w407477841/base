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
 * 该表存储用户命令被禁止/允许的权限。
 * </p>
 *
 * @author wyf
 * @since 2017-12-08
 */
@TableName("t_user_right_limit")
public class UserRightLimit extends Model<UserRightLimit> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
	@TableField("right_id")
	private Long rightId;
	@TableField("user_id")
	private Long userId;
    /**
     * 1禁止 0允许
     */
	private String limited;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRightId() {
		return rightId;
	}

	public void setRightId(Long rightId) {
		this.rightId = rightId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLimited() {
		return limited;
	}

	public void setLimited(String limited) {
		this.limited = limited;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "UserRightLimit{" +
			"id=" + id +
			", rightId=" + rightId +
			", userId=" + userId +
			", limited=" + limited +
			"}";
	}
}
