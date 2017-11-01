package com.cotroc.loginservice.objects;

public class User {
	
	private int id;
	private String name;
	private String pwd;
	private String mail;
	private boolean active;
	
	
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	public User(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public User() {
		
	}
	
	@Override
	public String toString() {	
		return "{\"id\":\""+id+"\","
			 + "\"name\":\""+name+"\","
			 + "\"pwd\":\""+pwd+"\","
			 + "\"mail\":\""+mail+"\","
			 + "\"active\":\""+active+"\"}";
	}
}
