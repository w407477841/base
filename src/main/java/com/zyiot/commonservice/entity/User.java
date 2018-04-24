package com.zyiot.commonservice.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
/**
 * 
 * UserDetailService对应的实体对象
 *
 */
@ApiModel(value="用户")
@TableName(value="t_user")
public class User implements UserDetails{
	private static final long serialVersionUID = 1L;
@TableId(type=IdType.AUTO,value="id")
@ApiModelProperty(value="主键")
	private Long id;
@ApiModelProperty(value="用户名")
@TableField(value="username")
	private String username;
@ApiModelProperty(value="真实姓名")
@TableField(value="realname")
	private String realname;
@ApiModelProperty(value="密码")
@TableField(value="password")
	private String password;
@ApiModelProperty(value="令牌")
@TableField(value="access_token")
	private String accessToken;
@ApiModelProperty(value="状态")
@TableField(value="status")
	private String status;
@TableField(exist=false)
private List<Role> roles = new ArrayList<Role>();
@TableField(exist=false)
private Long[] roleIds;

@TableField(exist=false)
private List<Right> rights =new ArrayList<Right>();
 @TableField(exist=false)
private String oldPassword;

@TableField(exist=false)
private static  String defaultRolePrefix ="ROLE_" ;// @PreAuthorize("hasRole('ADMIN')")会给权限默认加上 role_

/**
 * 添加 核准状态 ，判断是否通过审批
 * @return
 */
@TableField(value="approved")
private String approved;

public String getApproved() {
	return approved;
}
public void setApproved(String approved) {
	this.approved = approved;
}
public Long[] getRoleIds() {
	return roleIds;
}
public void setRoleIds(Long[] roleIds) {
	this.roleIds = roleIds;
}
	public String getOldPassword() {
	return oldPassword;
}
public void setOldPassword(String oldPassword) {
	this.oldPassword = oldPassword;
}
	public List<Right> getRights() {
	return rights;
}
public void setRights(List<Right> rights) {
	this.rights = rights;
}
	public List<Role> getRoles() {
	return roles;
}
public void setRoles(List<Role> roles) {
	this.roles = roles;
}
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 个人权限生成逻辑 
	 * 
	 * 
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> auths =new ArrayList<>();
		for(Right right: rights){
			auths.add(new SimpleGrantedAuthority(defaultRolePrefix+right.getRightName()));
		}
		return auths;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {//未通过/未审核
		//return this.getApproved()==null?false:this.getApproved().equals("通过");
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		
		//return this.getStatus()==null?false:this.getStatus().equals("1");
		return true;
	}
	@Override
	public String toString() {
		String toString="User{" +
				"id=" + id +
				", username=" + username +
				", password=******" +
				", realname=" + realname +
				", status=" + status +
				", roleIds= %s"+
				"}";
		String roleidstring="[";
		if(this.roleIds!=null){
		for(Long roleId: this.roleIds){
			roleidstring+=roleId+",";
		}
		if(roleidstring.endsWith(",")){
			roleidstring=roleidstring.substring(0, roleidstring.length()-1);
		}
		}
		roleidstring+="]";
	return toString.replace("%s", roleidstring);
		
		
		
	}
	
	
	

}
