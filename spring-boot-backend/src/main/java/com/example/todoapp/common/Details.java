package com.example.todoapp.common;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class Details {

	@NotBlank
	private String room;
	
	@NotBlank
	private TimePeriod validFor;
}
