package com.zyiot.commonservice.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zyiot.commonservice.entity.ShangjiInfo;
import com.zyiot.commonservice.mapper.ShangjiInfoMapper;
import com.zyiot.commonservice.service.IShangjiInfoService;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 王一飞
 * @since 2018-03-06 15:46:30
 */
@Service
public class ShangjiInfoServiceImpl extends ServiceImpl<ShangjiInfoMapper, ShangjiInfo> implements IShangjiInfoService {

	@Autowired
	FormService   formService;
	@Autowired
	TaskService  taskService;
	@Autowired
	IdentityService identityService;
	@Value("${flow.shangji}")
	private String processDefinitionId;
	@Override
	public Page<ShangjiInfo> findShangjiInfoByPage(Page<ShangjiInfo> page, ShangjiInfo shangjiInfo) {
		return  page.setRecords(baseMapper.selectShangjiInfoByPage(page, shangjiInfo));
	}
	@Override
	public List<ShangjiInfo> findShangjiInfoAll(ShangjiInfo shangjiInfo) {
		return baseMapper.selectShangjiInfoAll(shangjiInfo);
	}
	@Transactional
	public boolean insertStart(ShangjiInfo info) {
		if( retBool(baseMapper.insert(info))){
			identityService.setAuthenticatedUserId(info.getYg());
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("id",Long.toString(info.getId()));
			formService.submitStartFormData(processDefinitionId, properties);
			return true;
		}
		
		
		return false;
	}
	@Transactional
	/**
	 * 审批
	 * @param info
	 * @param taskId
	 * @return
	 */
	
	public boolean editShenpi(ShangjiInfo info,String taskId){
		if( retBool(baseMapper.updateById(info))){
			Task task= taskService.createTaskQuery().taskId(taskId).singleResult();
			if(task==null){
				throw new RuntimeException("任务已被处理");
			}
			Map<String,String> formValues=new HashMap<String, String>();
			formService.submitTaskFormData(taskId, formValues);
			return true;
		}
		
		
		return false;
		
	}
	@Transactional
	public String flowStartShangji(ShangjiInfo info, String userId,
			String processDefinitionId) {
		if( retBool(baseMapper.insert(info))){
			return Long.toString(info.getId());
		}
		return null;
	}
	@Transactional
	public boolean flowCompleteShangji(ShangjiInfo info, String taskId) {
		if(info.getId()==null) return true;
		if(retBool(baseMapper.updateById(info))){
			return true;
		}
		
		return false;
	}
	

}