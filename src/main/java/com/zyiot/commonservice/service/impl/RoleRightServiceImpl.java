package com.zyiot.commonservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.enums.SqlMethod;
import com.baomidou.mybatisplus.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.toolkit.CollectionUtils;
import com.zyiot.commonservice.entity.RoleRight;
import com.zyiot.commonservice.mapper.RoleRightMapper;
import com.zyiot.commonservice.service.IRoleRightService;

/**
 *
 * @author 王一飞
 * @since 2017-12-13 13:36:01
 */
@Service
public class RoleRightServiceImpl extends ServiceImpl<RoleRightMapper, RoleRight> implements IRoleRightService {

	@Override
	public Page<RoleRight> findRoleRightByPage(Page<RoleRight> page, RoleRight roleRight) {
		return  page.setRecords(baseMapper.selectRoleRightByPage(page, roleRight));
	}
	@Override
	public List<RoleRight> findRoleRightAll(RoleRight roleRight) {
		return baseMapper.selectRoleRightAll(roleRight);
	}
	@Override
	@Transactional(rollbackFor=Exception.class)
	public boolean perms(List<RoleRight> roleRights) {
		if(roleRights==null||roleRights.size()==0) return true;
		
		int batchSize =30;
		
		Map<String,Object> delEntity=new HashMap<String, Object>();
		delEntity.put("role_id", roleRights.get(0).getRoleId());
		baseMapper.deleteByMap(delEntity);//删除 当前角色下所有权限
		  if (CollectionUtils.isEmpty(roleRights)) {//批量添加
	            throw new IllegalArgumentException("Error: entityList must not be empty");
	        }
	        try (SqlSession batchSqlSession = sqlSessionBatch()) {
	            int size = roleRights.size();
	            String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
	            for (int i = 0; i < size; i++) {
	                batchSqlSession.insert(sqlStatement, roleRights.get(i));
	                if (i >= 1 && i % batchSize == 0) {
	                    batchSqlSession.flushStatements();
	                }
	            }
	            batchSqlSession.flushStatements();
	        } catch (Throwable e) {
	            throw new MybatisPlusException("Error: Cannot execute insertBatch Method. Cause", e);
	        }
	        return true;
		
	}
	

}