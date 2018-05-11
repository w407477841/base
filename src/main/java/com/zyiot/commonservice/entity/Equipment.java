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
 * @since 2018-05-08
 */
@TableName("t_equipment")
public class Equipment extends Model<Equipment> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 设备唯一标识
     */
	private String key;
    /**
     *  设备名称
     */
	private String name;
    /**
     * 所属工厂
     */
	@TableField("factory_id")
	private Long factoryId;
    /**
     * 设备类型:power/instrument 动力/仪表
     */
	private String type;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(Long factoryId) {
		this.factoryId = factoryId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Equipment{" +
			"id=" + id +
			", key=" + key +
			", name=" + name +
			", factoryId=" + factoryId +
			", type=" + type +
			"}";
	}
}
