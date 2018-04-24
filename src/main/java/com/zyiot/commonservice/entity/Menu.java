package com.zyiot.commonservice.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2018-03-15
 */
@TableName("t_menu")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 页面地址，只有子菜单需要，父级目录不需要填写
     */
	private String url;
    /**
     * 名称
     */
	private String txt;
    /**
     * 菜单状态：1开启、2关闭
     */
	private Integer status;
    /**
     * 父级菜单
     */
	private Long pid;
	@TableField(exist=false)
	private List<Menu> childs=new ArrayList<Menu>();
	

	public List<Menu> getChilds() {
		return childs;
	}

	public void setChilds(List<Menu> childs) {
		this.childs = childs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Menu{" +
			"id=" + id +
			", url=" + url +
			", txt=" + txt +
			", status=" + status +
			", pid=" + pid +
			"}";
	}
}
