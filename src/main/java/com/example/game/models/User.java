package com.example.game.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.DBRef;


@Entity
@Table(name = "users")
public class User {
	// unicos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String id;
	
	@Column
	private String username;
	
	@Column
	private Date registrationDate;
	
	//@OneToMany(mappedBy = "user")
	/*
	@Column	
	@ElementCollection
	@DBRef
	private List<Game> games;*/
	
	public User(String id, String username, Date date) {
		this.id = id;
		this.username = username;
		this.registrationDate = date;
	}
	
	public User(String username, Date date) {
		this.username = username;
		this.registrationDate = date;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Date getDate() {
		return this.registrationDate;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setDate(Date date) {
		this.registrationDate = date;
	}
	
	
}
