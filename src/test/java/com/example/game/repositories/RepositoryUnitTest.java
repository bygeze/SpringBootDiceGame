package com.example.game.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import com.example.game.models.Game;
import com.example.game.repositories.IGameRepository;
import com.example.game.repositories.IUserRepository;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class RepositoryUnitTest {
	@Autowired
	private TestEntityManager entityManager;
	
	@Autowired
	IGameRepository gameRepository;
	
	@Autowired
	IUserRepository userRepository;
	
	@Test
	public void should_find_no_games_if_repository_is_empty() {
		Iterable<Game> games = gameRepository.findAll();
		
		assertThat(games).isEmpty();
	}
}

