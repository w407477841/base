package com.zyiot.commonservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;

import java.util.Date;

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
 * @since 2017-12-14
 */
@TableName(value="t_log")
@ApiModel(value="日志信息api")
public class Log extends Model<Log> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	@ApiModelProperty(value="ID",name="id")
	private Long id;
    /**
     * 执行方法
     */
	private String method;
    /**
     * 参数json
     */
	private String param;
	private String result;
    /**
     * 执行时间
     */
	@TableField("ope_time")
	private Date opeTime;
    /**
     * 消耗时间
     */
	@TableField("exp_time")
	private Long expTime;
    /**
     * 操作用户：根据token解析
     */
	private String operator;
    /**
     * 请求地址
     */
	private String url;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getOpeTime() {
		return opeTime;
	}

	public void setOpeTime(Date opeTime) {
		this.opeTime = opeTime;
	}

	public Long getExpTime() {
		return expTime;
	}

	public void setExpTime(Long expTime) {
		this.expTime = expTime;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Log{" +
			"id=" + id +
			", method=" + method +
			", param=" + param +
			", result=" + result +
			", opeTime=" + opeTime +
			", expTime=" + expTime +
			", operator=" + operator +
			", url=" + url +
			"}";
	}
}
