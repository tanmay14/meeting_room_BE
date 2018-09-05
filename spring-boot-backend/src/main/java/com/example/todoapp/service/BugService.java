package com.example.todoapp.service;

import static  com.example.todoapp.common.ListUtils.toList;
import static  com.example.todoapp.common.ObjectMapper.mapObjectWithExcludeFilter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.todoapp.common.ListUtils;
import com.example.todoapp.models.BugReport;

import com.example.todoapp.repositories.BugRepository;
import com.querydsl.core.types.Predicate;

@Service
public class BugService {
	
	@Autowired
	private BugRepository bugRepository;
	
	

	public List<BugReport> findAllBugs() {
		return bugRepository.findAll();
	}
	
	public List<BugReport> findAllBugsByUserName(String userName) {
		
		return bugRepository.findAllByUserName(userName);
	}

	public BugReport createNewBug(BugReport bugReport) {
		// TODO Auto-generated method stub
		bugReport.setStatus("Open");
		return bugRepository.save(bugReport);
	}	
	

}
