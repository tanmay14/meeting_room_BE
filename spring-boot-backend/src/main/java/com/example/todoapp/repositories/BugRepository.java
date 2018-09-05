package com.example.todoapp.repositories;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.example.todoapp.models.BugReport;
import com.example.todoapp.models.User;

	@Repository
	public interface BugRepository extends MongoRepository<BugReport, String>,QuerydslPredicateExecutor<BugReport> {
		
		

		BugReport findByUserName(String userName);

		List<BugReport> findAll();

		List<BugReport> findAllByUserName(String userName);
		

	}


