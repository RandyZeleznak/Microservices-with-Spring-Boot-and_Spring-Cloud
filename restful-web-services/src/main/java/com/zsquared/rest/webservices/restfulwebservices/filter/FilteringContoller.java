package com.zsquared.rest.webservices.restfulwebservices.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

// Filter Controller using dynamic filtering

@RestController
public class FilteringContoller {
	
	@GetMapping("/filtering")
	public MappingJacksonValue RetrieveSomeBean() {
		
		SomeBean someBean = new SomeBean("value1","value2","valule3");
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(someBean); 
		mapping.setFilters(filters);
		return mapping;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue RetrieveListSomeBean() {
		List<SomeBean> list = Arrays.asList(new SomeBean("value1","value2","valule3"),
				new SomeBean("value12","value22","valule32"));
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters = new SimpleFilterProvider().addFilter("someBeanFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(list); 
		mapping.setFilters(filters);
		
		return mapping;
	}

}
