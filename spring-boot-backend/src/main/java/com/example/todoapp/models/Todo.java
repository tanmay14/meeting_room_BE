package com.example.todoapp.models;

import java.net.URI;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.todoapp.common.Credentials;
import com.example.todoapp.common.Details;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.querydsl.core.annotations.QueryEntity;

import lombok.Data;

@Data
//@JsonFilter("userFilter")
@Document(collection="todos")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@QueryEntity
public class Todo {
	@Id
	    private String id;
	    
	    private String userName;
	 
	    private String start_date;	
	    
	    private String end_date;
	    
	    private String text;

		private String roomName;
}