package com.bham.fsd.assignments.jabberserver;

import javafx.beans.property.SimpleStringProperty;

public class Jab {
	private final SimpleStringProperty username;
	private final SimpleStringProperty jabtext;
	private final SimpleStringProperty jabid;
	private final SimpleStringProperty likescount;
	
	
	public Jab() {
		this.username = new SimpleStringProperty("");
		this.jabtext = new SimpleStringProperty("");
		this.jabid = new SimpleStringProperty("");
		this.likescount = new SimpleStringProperty("");
		
	}
	
	public Jab(String username, String jabtext, String jabid, String likescount) {
		super();
		this.username = new SimpleStringProperty(username);
		this.jabtext = new SimpleStringProperty(jabtext);
		this.jabid = new SimpleStringProperty(jabid);
		this.likescount = new SimpleStringProperty(likescount);
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
	
	public String getJabText() {
		return jabtext.get();
	}
	
	public void setJabtext(String jabtext) {
		this.jabtext.set(jabtext);
	}
	
	public String getJabID() {
		return jabid.get();
	}
	
	public void setJabID(String jabid) {
		this.jabid.set(jabid);
	}
	
	public String getLikesCount() {
		return likescount.get();
	}
	
	public void setLikesCount(String likescount) {
		this.likescount.set(likescount);
	}
	
	
}