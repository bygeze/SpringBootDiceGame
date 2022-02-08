package com.example.game.models;

import java.util.Random;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

//@Document("games")
public class MongoGame {
	
	@Id
	private String id;
	private boolean result;
	private int dado1;
	private int dado2;
	private String userId;
	
	public MongoGame() {
		
	}
	
	public MongoGame(String user_id) {
		play();
		this.userId = user_id;
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public int getDado1() {
		return dado1;
	}
	
	public void setDado1(int dado1) {
		this.dado1 = dado1;
	}
	
	public int getDado2() {
		return dado2;
	}
	
	public void setDado2(int dado2) {
		this.dado2 = dado2;
	}
	
	public boolean getResult() {
		return result;
	}
	
	public void setResult(Boolean result) {
		this.result = result;
	}
	
	public MongoGame(String id, boolean result, int dado1, int dado2) {
		this.id = id;
		this.result = result;
		this.dado1 = dado1;
		this.dado2 = dado2;
	}
	
	private void play() {
		dado1 = throwDice();
		dado2 = throwDice();
		
		if(dado1 + dado2 == 7) {
			result = true;
		} else {
			result = false;
		}
	}
	
	private int throwDice() {
		Random ran = new Random();
		return 1 + ran.nextInt(6 - 1 + 1);
	}
}
