package com.example.game.repositories;

import java.util.List;

import com.example.game.models.User;

public interface IUserRepositoryCustom {
	public List<User> findByUsername(String username); 
}
