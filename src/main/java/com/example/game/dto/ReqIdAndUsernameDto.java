package com.example.game.dto;

public class ReqIdAndUsernameDto {
	private Long id;
	private String username;
	
	public ReqIdAndUsernameDto(Long id, String username) {
		this.setId(id);
		this.setUsername(username);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
