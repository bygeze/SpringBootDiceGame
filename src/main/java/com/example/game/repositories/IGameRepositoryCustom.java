package com.example.game.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.game.models.Game;

@Repository

public abstract interface IGameRepositoryCustom {
	@Query(value = "SELECT * FROM games WHERE user_id = ?1", nativeQuery = true)
	public List<Game> findAllByUser(Long id);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM games WHERE user_id = ?1", nativeQuery = true)
	public Integer deleteAllByUser(Long id);
}
