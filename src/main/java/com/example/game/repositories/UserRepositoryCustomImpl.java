package com.example.game.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.example.game.models.User;

@Repository
public class UserRepositoryCustomImpl implements IUserRepositoryCustom {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<User> findByUsername(String username) {
		// TODO Auto-generated method stub
		TypedQuery<User> query = em.createQuery(
				"SELECT * FROM users WHERE username = ?1", User.class);
		
		List<User> user = query.setParameter(1, username).getResultList();
		
		return user;
	}

}
