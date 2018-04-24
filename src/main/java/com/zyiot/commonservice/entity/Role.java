package com.zyiot.commonservice.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.activerecord.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author wyf
 * @since 2017-12-05
 */
@TableName(value="t_role")
public class Role extends Model<Role> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Long id;
    /**
     * 角色名
     */
	@TableField("role_name")
	private String roleName;
    /**
     * 简介
     */
	private String intro;
    /**
     * 删除状态  1：已删除  0未删除
     */
	private String deleted;
	@TableField(exist=false)
	private List<Right> rights=new ArrayList<Right>();
	@TableField(exist=false)
	private List<Long>  rightIds=new ArrayList<Long>();
	@TableField(exist=false)
	private List<Menu> menus=new ArrayList<Menu>();
	@TableField(exist=false)
	private List<Long> menuIds =new ArrayList<Long>();
	
	
	public List<Menu> getMenus() {
		return menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public List<Long> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<Long> menuIds) {
		this.menuIds = menuIds;
	}

	public List<Long> getRightIds() {
		return rightIds;
	}

	public void setRightIds(List<Long> rightIds) {
		this.rightIds = rightIds;
	}

	public List<Right> getRights() {
		return rights;
	}

	public void setRights(List<Right> rights) {
		this.rights = rights;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

	@Override
	public String toString() {
		return "Role{" +
			"id=" + id +
			", roleName=" + roleName +
			", intro=" + intro +
			", deleted=" + deleted +
			"}";
	}
}
