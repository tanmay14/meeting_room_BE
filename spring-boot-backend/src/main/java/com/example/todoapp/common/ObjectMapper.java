package com.example.todoapp.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.MultiValueMap;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class ObjectMapper {

	public static MappingJacksonValue mapObjectWithExcludeFilter(Object object,
			MultiValueMap<String, String> requestParams, String filter) {

		Set<String> fields = new HashSet<>();

		if (null != requestParams && requestParams.size() > 0) {

			if (requestParams.containsKey("fields")) {
				List<String> fieldList = requestParams.get("fields");
				for (String field : fieldList) {
					fields.addAll(Arrays.asList(field.split(",")));
				}
				fields.add("id");
			}

		}

		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(object);
		SimpleFilterProvider filters = new SimpleFilterProvider().setFailOnUnknownId(false);
		if (null != fields && fields.size() > 0) {
			filters.addFilter(filter, SimpleBeanPropertyFilter.filterOutAllExcept(fields));

		}
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}

}
