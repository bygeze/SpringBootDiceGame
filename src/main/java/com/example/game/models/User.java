package com.example.game.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {
	// unicos
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String username;
	
	@Column
	private Date registrationDate;
	
	@OneToMany(mappedBy = "user")
	private List<Game> games;
	
	public User() {
		
	}
	
	public User(String username, Date date) {
		this.username = username;
		this.registrationDate = date;
	}
	
	public Long getId() {
		return this.id;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public Date getDate() {
		return this.registrationDate;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void setDate(Date date) {
		this.registrationDate = date;
	}
	
	
}
