package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2018-03-27
 */
@TableName("t_factory")
public class Factory extends Model<Factory> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 编号
     */
	private String bh;
    /**
     * 工厂名称
     */
	private String name;
    /**
     * 地址
     */
	private String addr;
	private String phone;
    /**
     * 负责人-关联用户表
     */
	private Long owner;
	@TableField(exist=false)
	private List<Map<String, Object>> owners =new ArrayList<Map<String,Object>>();
	
	public List<Map<String, Object>> getOwners() {
		return owners;
	}

	public void setOwners(List<Map<String, Object>> owners) {
		this.owners = owners;
	}

	@TableField(exist=false)
	private String ownerTxt;
	
    public String getOwnerTxt() {
		return ownerTxt;
	}

	public void setOwnerTxt(String ownerTxt) {
		this.ownerTxt = ownerTxt;
	}

	/**
     * 开始日期
     */
	@TableField("start_day")
	private String startDay;
    /**
     * 结束日期
     */
	@TableField("end_day")
	private String endDay;
    /**
     * 最大用户数
     */
	private Integer max;
    /**
     * 工厂状态 正常\到期 \禁用
     */
	private String status;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

	public String getStartDay() {
		return startDay;
	}

	public void setStartDay(String startDay) {
		this.startDay = startDay;
	}

	public String getEndDay() {
		return endDay;
	}

	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}

	public Integer getMax() {
		return max;
	}

	public void setMax(Integer max) {
		this.max = max;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Factory{" +
			"id=" + id +
			", bh=" + bh +
			", name=" + name +
			", addr=" + addr +
			", phone=" + phone +
			", owner=" + owner +
			", startDay=" + startDay +
			", endDay=" + endDay +
			", max=" + max +
			", status=" + status +
			"}";
	}
}
