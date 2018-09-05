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
@Document(collection="users")
@JsonIgnoreProperties(value = {"createdAt"}, allowGetters = true)
@QueryEntity
public class User {
	
	 @Id
	    private String id;
	 
	 @Transient
		private URI href;
	 
	    private String index;
	    
	    private Boolean isActive;
	    
	    private Boolean userCreated;
	    
	    private String role;	    
	    
	    private String firstName;
	    
	    private String lastName;
	    
	    private String teamName;
	    
	    private String emailId;    
	    
	    private String registered;
	    
	    private String userName;
	    
	    private String password;
	    
	    private String confirmPassword;
	    
	    private String bookedRoom;
	    
	    private String dateFrom;	
	    
	    private String dateTo;
	    
	    private String resetToken;
	    
	    
//	    public String toString() {
//	        return String.format(
//	                "Todo[id=%s, title='%s', completed='%s']",
//	                id, title, completed);
//	    }

}
