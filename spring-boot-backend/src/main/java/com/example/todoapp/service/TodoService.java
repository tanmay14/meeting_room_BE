package com.example.todoapp.service;

import static  com.example.todoapp.common.ListUtils.toList;
import static  com.example.todoapp.common.ObjectMapper.mapObjectWithExcludeFilter;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
import com.example.todoapp.models.Todo;
import com.example.todoapp.repositories.TodoRepository;
import com.querydsl.core.types.Predicate;

@Service
public class TodoService {

	@Autowired
	private TodoRepository todoRepository;



	public List<Todo> findAllTodos() {
		return todoRepository.findAll();
	}

	public List<Todo> findAllTodosByUserName(String userName) {
System.out.println("Todo Service username"+userName);
		return todoRepository.findAllByUserName(userName);
	}
	
	public Optional<Todo> findById(String id) {

		return todoRepository.findById(id);
	}

	public Todo createNewTodo(Todo todo) {
		// TODO Auto-generated method stub		
		todo.setRoomName(RoomName(todo));
		todo.setUserName(UserName(todo));
		return todoRepository.save(todo);
	}

	public void deleteTodo(String id) {
		// TODO Auto-generated method stub
		todoRepository.deleteById(id);


	}
	
	private Todo getExistingTodos(String id) {
		
		 Optional<Todo> existingTodosOption=todoRepository.findById(id);
		 if(!existingTodosOption.isPresent()) {
		throw new IllegalArgumentException("No event by the id "+id+" present");
		}
		 Todo existingTodo=existingTodosOption.get();
		 return existingTodo;
	}

	public Todo updateTodo(String id,Todo newtodo) {
		// TODO Auto-generated method stub
		Todo original=getExistingTodos(id);
			
			if(!original.getStart_date().equals(newtodo.getStart_date())) {
				original.setStart_date(newtodo.getStart_date());
			}
			if (!original.getEnd_date().equals(newtodo.getEnd_date())) {
				original.setEnd_date(newtodo.getEnd_date());
			}
			if (!original.getText().equals(newtodo.getText())) {
				original.setText(newtodo.getText());
				original.setRoomName(RoomName(newtodo));
				
			}

			return todoRepository.save(original); 
	}	
	
	public String RoomName(Todo todo) {
		
		String[] text=null;
		text=todo.getText().trim().split("@");
		String roomName=null;
		if(text[1].equalsIgnoreCase("saina")) {
			roomName="Saina";
		}
		else if(text[1].equalsIgnoreCase("sakshi")) {
			roomName="Sakshi";
		}
		else if(text[1].equalsIgnoreCase("kondana")) {
			roomName="Kondana";
		}
		else if(text[1].equalsIgnoreCase("jaigad")) {
			roomName="Jaigad";
		}
		else if(text[1].equalsIgnoreCase("sindhu")) {
			roomName="Sindhu";
		}
		else {
			roomName=null;			
		}
		return roomName;
	}
	
	public String UserName(Todo todo) {
		String[] text=null;
		text=todo.getText().trim().split("@");
		String userName=null;
		if(StringUtils.isNotBlank(text[0])) {
			userName=text[0];
		}
		else {
			userName="admin";
		}	
		return userName;
	}
	
	
}
