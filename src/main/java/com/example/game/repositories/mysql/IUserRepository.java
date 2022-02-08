package com.example.game.repositories.mysql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.game.models.User;

@Repository
public interface IUserRepository extends CrudRepository<User, Long>, IUserRepositoryCustom {

}
