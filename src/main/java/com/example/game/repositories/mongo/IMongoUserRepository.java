package com.example.game.repositories.mongo;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.game.models.MongoUser;
import com.example.game.models.User;

@Repository
public interface IMongoUserRepository extends MongoRepository<MongoUser, Long>{
	public Optional<MongoUser> findByUsername(String username);
	
}
