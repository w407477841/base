package com.zyiot.commonservice.pojo;

public class TaskRepresentation {
	
	public TaskRepresentation() {
		super();
		// TODO Auto-generated constructor stub
	}

	private String id;

	private String name;
	private String processInstanceId;
	private String assignee;
	private String createTime;
	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}



	public TaskRepresentation(String id, String name, String processInstanceId,
			String assignee, String createTime) {
		super();
		this.id = id;
		this.name = name;
		this.processInstanceId = processInstanceId;
		this.assignee = assignee;
		this.createTime = createTime;
	}

	public String getId(){

	return id;
	}

	public void setId(String id){

	this.id = id;
	}

	public String getName(){

	return name;
	}

	public void setName(String name){

	this.name = name;
	}
	
}
