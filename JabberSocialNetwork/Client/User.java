package com.bham.fsd.assignments.jabberserver;
import javafx.beans.property.SimpleStringProperty;

public class User {
	private final SimpleStringProperty username;
	
	public User() {
		this.username = new SimpleStringProperty("");
	}
	
	public User(String username) {
		super();
		this.username = new SimpleStringProperty(username);
	}
	
	public String getUsername() {
		return username.get();
	}
	
	public void setUsername(String username) {
		this.username.set(username);
	}
}
