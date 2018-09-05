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
@Document(collection="bugreport")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@QueryEntity
public class BugReport {

	@Id
	private String id;

	private String userName;

	private int priority;

	private String comment;

	private String status;



	//	    public String toString() {
	//	        return String.format(
	//	                "Todo[id=%s, title='%s', completed='%s']",
	//	                id, title, completed);
	//	    }

}
