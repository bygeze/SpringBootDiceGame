package com.example.game.dto;

public class AuthUser {
	public String username;
	public String token;
	
	public AuthUser(String u, String t) {
		this.username = u;
		this.token = t;
	}
}
