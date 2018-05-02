package com.zyiot.commonservice.push.entity;

public class Notify {

	private String platform;
	private String audience;
	private Notification notification;
	
	
	
	
	public Notify() {
	}
	
	
	
	public Notify(String message) {
		
		setPlatform("all");
		setAudience("all");
		Notification no=	new Notification();
		no.setAlert(message);
		setNotification(no);
	}



	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	
}

class Notification{
	private String alert;

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}
	
}
