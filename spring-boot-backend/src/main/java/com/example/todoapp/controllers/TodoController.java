package com.example.todoapp.controllers;

import javax.validation.Valid;

import com.example.todoapp.models.BugReport;
import com.example.todoapp.models.Todo;
import com.example.todoapp.models.User;
import com.example.todoapp.repositories.TodoRepository;
import com.example.todoapp.service.TodoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class TodoController {

	@Autowired
	TodoService todoService;

	//Bug service get all bugs irrespective of user
	@RequestMapping(method = RequestMethod.GET, value = "/allTodo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Todo>> GetAllTodos() {
		try {

			List<Todo> todos = todoService.findAllTodos();


			return new ResponseEntity<>(todos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//Bug service get all bugs irrespective of user
	@SuppressWarnings("unchecked")
	@RequestMapping(method = RequestMethod.GET, value = "/roomStats", produces = MediaType.APPLICATION_JSON_VALUE)
	public HashMap<String, Integer> GetRoomStats() {
		try {

			List<Todo> todos = todoService.findAllTodos();
			HashMap<String, Integer> roomStats=new  HashMap<>();
			String roomName;
			int roomCount=0;

			Iterator<Todo> itr = todos.iterator();
			while (itr.hasNext()) {
				roomName=itr.next().getRoomName();
				System.out.println("roomName::::"+roomName);
				if(roomStats.containsKey(roomName)) {
					int currentCount=roomStats.get(roomName)+1;
					System.out.println(currentCount);
					roomStats.remove(roomName);
					roomStats.put(roomName, currentCount);

				}
				else {
				roomStats.put(roomName, 1);  
				}
			}

			System.out.println("::::::::::::::::::::::::::::::::::::::");

			Iterator it = roomStats.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry)it.next();
				System.out.println(pair.getKey()+"::::::::::::::"+pair.getValue());



			}
			return roomStats;

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}


	//get all bugs for a particular user
	@RequestMapping(method = RequestMethod.POST, value = "/allTodoPerUser", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Todo>> GetAllTodoPerUser(@RequestBody User user) {
		try {

			List<Todo> todos = todoService.findAllTodosByUserName(user.getUserName());


			return new ResponseEntity<>(todos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


	// create new bug

	@RequestMapping(method = RequestMethod.POST, value = "/createNewTodo", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Todo> CreateNewTodo(@RequestBody Todo todo) {
		try {

			Todo todos = todoService.createNewTodo(todo);


			return new ResponseEntity<Todo>(todos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	//delete event by id
	@RequestMapping(method = RequestMethod.DELETE, value = "/deleteTodo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> DeleteTodo(@PathVariable String id) {
		try {

			todoService.deleteTodo(id);


			return new ResponseEntity<>( HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/updateTodo/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Todo> UpdateTodo(@PathVariable String id,@RequestBody Todo todo) {
		try {

			Todo todos=todoService.updateTodo(id,todo);


			return new ResponseEntity<Todo>(todos, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}