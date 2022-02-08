package com.example.game.repositories.mongo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.game.models.MongoGame;

@Repository
public interface IMongoGameRepository extends MongoRepository<MongoGame, Long>{
	public List<MongoGame> findAllByUserId(String id);
	/*
	public Optional<Game> findById(Long id);
	public List<Game> findAllByUserId(Long id);
	public int deleteAllByUserId(Long id);*/
}
