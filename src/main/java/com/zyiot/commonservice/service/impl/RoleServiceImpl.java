package com.zyiot.commonservice.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.zyiot.commonservice.common.util.BatchUtil;
import com.zyiot.commonservice.entity.Menu;
import com.zyiot.commonservice.entity.Right;
import com.zyiot.commonservice.entity.Role;
import com.zyiot.commonservice.entity.RoleMenu;
import com.zyiot.commonservice.entity.RoleRight;
import com.zyiot.commonservice.mapper.MenuMapper;
import com.zyiot.commonservice.mapper.RightMapper;
import com.zyiot.commonservice.mapper.RoleMapper;
import com.zyiot.commonservice.mapper.RoleMenuMapper;
import com.zyiot.commonservice.mapper.RoleRightMapper;
import com.zyiot.commonservice.service.IRoleService;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 王一飞
 * @since 2017-12-13 13:36:00
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
@Autowired
	RightMapper  rightMapper;
@Autowired
RoleRightMapper roleRightMapper;

@Autowired
MenuMapper  menuMapper;
@Autowired
RoleMenuMapper roleMenuMapper;	
	@Override
	public Page<Role> findRoleByPage(Page<Role> page, Role role) {
		return  page.setRecords(baseMapper.selectRoleByPage(page, role));
	}
	@Override
	public List<Role> findRoleAll(Role role) {
		return baseMapper.selectRoleAll(role);
	}
	@Override
	public Role selectRoleRightByid(Serializable id) {
		
		Role role=baseMapper.selectById(id);
		
		Wrapper<Right> r=new EntityWrapper<Right>();
		r.orderBy("module_id,id");
		//所有权限
		List<Right> rights=rightMapper.selectList(r);
		role.setRights(rights);
		//角色拥有的权限
		List<Right> hasList=rightMapper.selectRightAllByRole(id);
		List<Long> rightIds =new ArrayList<Long>();
		for(int i=0;hasList!=null&&i<hasList.size();i++){
			rightIds.add(hasList.get(i).getId());
		}
		role.setRightIds(rightIds);
		
		//所有功能
		List<Menu> menuall=menuMapper.selectMenuAll(new Menu());
		List<Menu> result=new ArrayList<Menu>();
		//找到所有根目录
		for( Menu m: menuall ){
			if(m.getPid().equals(Long.parseLong("0"))){
				result.add(	deidai(m,menuall));
			}
		}
		role.setMenus(result);
		
		
		
		//角色拥有的功能
		List<Menu> hashMenus=menuMapper.selectMenuAllByRoleId(id);
		List<Long> menuIds =new ArrayList<Long>();
		for(int i=0;hashMenus!=null&&i<hashMenus.size();i++){
			menuIds.add(hashMenus.get(i).getId());
		}
		role.setMenuIds(menuIds);
		
		return role;
	}
	
	private Menu deidai(Menu current,List<Menu> all){
		List<Menu> childs= new ArrayList<Menu>();
		for(Menu m:all){
			if(m.getPid().equals(current.getId())){
				childs.add(deidai(m,all));
			}
		}
		current.setChilds(childs);
		
		return current;
	}
	/**
	 * 修改 角色的权限
	 */
	@Transactional(rollbackFor={Exception.class})
	public boolean updateRoleRight(Role role) {
		//删除角色下的所有权限
		Wrapper<RoleRight> delWrapper=new EntityWrapper<RoleRight> ();
		delWrapper.eq("role_id", role.getId());
		roleRightMapper.delete(delWrapper);
		//封装关系对象
		List<RoleRight> roleRights =new ArrayList<RoleRight>();
		for(Long rightId:role.getRightIds() ){
			RoleRight roleRight=new RoleRight();
			roleRight.setRightId(rightId);
			roleRight.setRoleId(role.getId());
			roleRights.add(roleRight);
		}
		
		if(roleRights==null||roleRights.size()==0){
			throw new IllegalArgumentException("rightIds不能为空");
		}
		BatchUtil<RoleRight> batchUtil =new BatchUtil<RoleRight>();
		
			try {
				batchUtil.batchInsert(RoleRight.class, roleRights);
			} catch (Exception e) {
				throw new MybatisPlusException("SQL执行错误,请检查RoleRight对象");
			}
		
		
		//删除角色下所有功能
		Wrapper<RoleMenu> delWrapper2=new EntityWrapper<RoleMenu>();
		delWrapper2.eq("role_id", role.getId());
		roleMenuMapper.delete(delWrapper2);
		//分装对象
		List<RoleMenu> roleMenus=new ArrayList<RoleMenu>();
		for(Long menuId:role.getMenuIds()){
			RoleMenu roleMenu=new RoleMenu();
			roleMenu.setMenuId(menuId);
			roleMenu.setRoleId(role.getId());
			roleMenus.add(roleMenu);
		}
		
		if(roleMenus==null||roleMenus.size()==0){
			throw new IllegalArgumentException("menuIds不能为空");
		}
		
		BatchUtil<RoleMenu> batchUtil2 =new BatchUtil<RoleMenu>();
		try{
		batchUtil2.batchInsert(RoleMenu.class,roleMenus);
		} catch (Exception e) {
			throw new MybatisPlusException("SQL执行错误,请检查RoleMenu对象");
		}
		return true;
	}

}