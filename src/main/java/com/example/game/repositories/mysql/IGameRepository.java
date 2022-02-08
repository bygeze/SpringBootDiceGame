package com.example.game.repositories.mysql;

import org.hibernate.annotations.Table;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.game.models.Game;

@Repository
public interface IGameRepository extends CrudRepository<Game, Long>, IGameRepositoryCustom {

}
