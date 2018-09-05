package com.example.todoapp.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;


import com.example.todoapp.models.BugReport;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.service.BugService;
import com.querydsl.core.types.Predicate;

import static com.example.todoapp.common.ObjectMapper.mapObjectWithExcludeFilter;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class BugController {
	
	@Autowired
	private BugService bugService;	
	

	
	
	//Bug service get all bugs irrespective of user
	@RequestMapping(method = RequestMethod.GET, value = "/allBugs", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BugReport>> GetAllBugs() {
		try {
			
			List<BugReport> bugs = bugService.findAllBugs();
			
			
			return new ResponseEntity<>(bugs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	//get all bugs for a particular user
	@RequestMapping(method = RequestMethod.POST, value = "/allBugsPerUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BugReport>> GetAllBugsPerUser(@RequestBody BugReport bugReport) {
		try {
			
			List<BugReport> bugs = bugService.findAllBugsByUserName(bugReport.getUserName());
			
			
			return new ResponseEntity<>(bugs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	// create new bug
	
	@RequestMapping(method = RequestMethod.POST, value = "/createNewBug", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<BugReport> CreateNewBug(@RequestBody BugReport bugReport) {
		try {
			
			BugReport bugs = bugService.createNewBug(bugReport);
			
			
			return new ResponseEntity<BugReport>(bugs, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	
}
