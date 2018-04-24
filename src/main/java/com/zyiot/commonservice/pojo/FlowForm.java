package com.zyiot.commonservice.pojo;

public class FlowForm {

	private String id ;
	private String url;
	private String table;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public FlowForm() {
		super();
		// TODO Auto-generated constructor stub
	}
	public FlowForm(String id, String url, String table) {
		super();
		this.id = id;
		this.url = url;
		this.table = table;
	}
	
	
}
