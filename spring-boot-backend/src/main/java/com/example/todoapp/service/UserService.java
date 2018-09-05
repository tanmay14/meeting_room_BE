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

import com.example.todoapp.models.User;
import com.example.todoapp.repositories.UserRepository;
import com.querydsl.core.types.Predicate;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User createUser(@Valid User user) {

		if (user.getId() != null) {
			throw new IllegalArgumentException("id must be empty while creating User");
		}

		setDefaultValues(user);

		return userRepository.save(user);
	}

	public User findUser(@NotNull String id) {
		return userRepository.findById(id).get();
	}
	
	
	//login
	public User findByUserNameAndPassword(User user) {
		try {
		 return userRepository.findByUserNameIgnoreCaseAndPassword(user.getUserName(),user.getPassword());
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	//signup create new user
	public User findByUserName(User user) {
		try {
		 return userRepository.findByUserNameIgnoreCase(user.getUserName());
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	public User saveUser(User user) {
		try {
			User userWithDefValue = null;
			userWithDefValue=setDefaultValues(user);
			System.out.println("save user::::"+userWithDefValue);
		 return userRepository.save(userWithDefValue);
		}
		catch(Exception e) {
			
			throw new RuntimeException(e);
		}
	}
	
	

	public List<User> findAllUsers(Predicate predicate) {

		if (null == predicate) {
			return userRepository.findAll();
		}
		
		return toList(userRepository.findAll(predicate));
	}
	

	

	public void deleteUser(@NotNull String id) {
		User existingUser = getExistingUser(id);
		userRepository.delete(existingUser);

	}

	public User updateUser(@Valid User user) {

		return userRepository.save(user);
	}

	public User partialUpdateUser(User user) {

		if (null == user.getUserName()) {
			throw new IllegalArgumentException("userName is mandatory field for update.");
		}

		User existingUser = getExistingUserByUserName(user.getUserName());
		System.out.println("original password:::::"+existingUser.getPassword());
		System.out.println("user entered original password"+user.getPassword());
		if(!existingUser.getPassword().equals(user.getPassword())) {
			existingUser.setPassword(user.getConfirmPassword());
			System.out.println("Password changed Successfully");
		}
		else {
			System.out.println("old and new password same");
			
		}

		return userRepository.save(existingUser);

	}

//	private User saveUser(@Valid User user) {
//		return userRepository.save(user);
//	}

	private User getExistingUserByUserName(String userName) {	
		
		return userRepository.findByUserNameIgnoreCase(userName);
	}

	private User setDefaultValues(User user) {
		
		user.setIsActive(true);
		user.setRegistered(new Date().toString());
		user.setRole("User");
		user.setUserCreated(true);
		return user;
		
	}

	private User getExistingUser(@NotNull String id) {
		Optional<User> existingUserOption = userRepository.findById(id);
		if (!existingUserOption.isPresent()) {
			throw new IllegalArgumentException("ServiceUser with id " + id + " doesnot exists");
		}

		User existingUser = existingUserOption.get();
		return existingUser;
	}

	public List<User> findAll() {
		// TODO Auto-generated method stub
		List<User> allUser=userRepository.findAll();
		System.out.println(allUser);
		return allUser;
	}

	public User findUserByEmail(User user){
		  return userRepository.findByEmailId(user.getEmailId());
	  }
	  public User findUserByResetToken(String resetToken){
		  return userRepository.findByResetToken(resetToken);
	  }

	
	

}
