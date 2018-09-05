package com.example.todoapp.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


import com.example.todoapp.models.User;

	@Repository
	public interface UserRepository extends MongoRepository<User, String>,QuerydslPredicateExecutor<User> {
		
		

		User findByUserNameIgnoreCaseAndPassword(String username, String password);
		User findByUserNameIgnoreCase(String username);
		User findByEmailId(String emailId);
		User findByResetToken(String resetToken);

	}


