package com.zyiot.commonservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.rabbitmq.client.AMQP.Basic.Return;
import com.zyiot.commonservice.entity.Menu;
import com.zyiot.commonservice.mapper.MenuMapper;
import com.zyiot.commonservice.service.IMenuService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.springframework.beans.factory.support.ChildBeanDefinition;
import org.springframework.stereotype.Service;

/**
 *
 * @author 王一飞
 * @since 2018-03-15 09:10:10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Override
	public Page<Menu> findMenuByPage(Page<Menu> page, Menu menu) {
		return  page.setRecords(baseMapper.selectMenuByPage(page, menu));
	}
	@Override
	public List<Menu> findMenuAll(Menu menu) {
		return baseMapper.selectMenuAll(menu);
	}
	@Override
	public List<Menu> findRoleAllSort(String username) {
		List<Menu> menuall=baseMapper.selectMenuAllByUsername(username);
		List<Menu> result=new ArrayList<Menu>();
		//找到所有根目录
		for( Menu m: menuall ){
			if(m.getPid().equals(Long.parseLong("0"))){
				result.add(	deidai(m,menuall));
			}
		}
		
		return result;
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
	@Override
	public Page<Menu> findOrMenuByPage(Page<Menu> page, String key) {
		return page.setRecords(baseMapper.selectOrMenuByPage(page, key)) ;
	}
	
}