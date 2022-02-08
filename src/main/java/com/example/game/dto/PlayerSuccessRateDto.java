package com.example.game.dto;

public class PlayerSuccessRateDto implements ResponseDto {
	public String name;
	public int successRate;
	
	public PlayerSuccessRateDto(String name, int successRate) {
		this.name = name;
		this.successRate = successRate;
	}
}
