package com.example.game.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

//@Document("users")
public class MongoUser {
	@Id
	private String id;
	
	@Column
	private String username;
	
	@Column
	private Date regDate;


	public MongoUser(String u, Date d) {
		this.username = u;
		this.regDate = d;
	}
	
	public MongoUser() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getRegDate() {
		return regDate;
	}
	
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
}
