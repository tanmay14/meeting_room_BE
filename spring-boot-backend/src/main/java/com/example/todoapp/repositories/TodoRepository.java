package com.example.todoapp.repositories;

import com.example.todoapp.models.Todo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String> {
	
	 Todo findByUserName(String userName);

	List<Todo> findAllByUserName(String userName);

	Todo save(Optional<Todo> original);

	List<Todo> findAllById(String id);

}