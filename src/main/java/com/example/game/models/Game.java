package com.example.game.models;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "games")
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private int dado1;
	
	@Column
	private int dado2;
	
	@Column
	private boolean result;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;
	
	public Game() {
		
	}
	
	public Game(User user) {
		this.user = user;
		play();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
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
