package com.example.todoapp.common;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.mongodb.core.index.Indexed;


import lombok.Data;
import lombok.EqualsAndHashCode;
@Data

public class Credentials {
	
	@NotBlank
    
	private String userName;
	
	@NotBlank
	private String password;

}
