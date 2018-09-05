package com.example.todoapp.common;

import javax.validation.constraints.NotNull;

import com.example.todoapp.common.Constant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(includeFieldNames = true)
public class TimePeriod {
	
	

	private static final String DATE_WITH_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm";

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WITH_TIME_PATTERN )
	@NotNull(message = "Start Date is mandatory")
	private String dateFrom;

//	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_WITH_TIME_PATTERN)
	private String dateTo;

}
