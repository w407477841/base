package com.zyiot.commonservice.websocket.entity;

public class Greeting {
	 private String content;  
	    private String type ;
	    private String from;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getFrom() {
			return from;
		}
		public void setFrom(String from) {
			this.from = from;
		}
		public Greeting(String content, String type, String from) {
			super();
			this.content = content;
			this.type = type;
			this.from = from;
		}
	    
	    
}
