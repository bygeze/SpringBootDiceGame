package com.example.game.dto;

public class GameDto implements ResponseDto {
	public String id;
	public int dado1;
	public int dado2;
	public boolean result;

	public GameDto() {
		
	}
	
	public GameDto(String string, int dado1, int dado2, boolean result) {
		this.id = string;
		this.dado1 = dado1;
		this.dado2 = dado2;
		this.result = result;
	}
	
	
}
