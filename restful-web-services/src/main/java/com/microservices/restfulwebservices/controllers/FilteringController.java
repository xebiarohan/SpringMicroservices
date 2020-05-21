package com.microservices.restfulwebservices.controllers;

import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.microservices.restfulwebservices.dtos.Employee;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilteringController {

    public Employee getStaticFilteredEmployee() {
        return new Employee("Alpha",3500000,8782378234l);
    }

    @GetMapping("/employee")
    public MappingJacksonValue getDynamicFilterEmployee() {
        Employee emp = new Employee("Alpha", 3500000, 8782378234l);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(emp);
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("name","phoneNumber");

        FilterProvider filters = new SimpleFilterProvider().addFilter("EmployeeFilter",filter);
        mappingJacksonValue.setFilters(filters);
        return mappingJacksonValue;
    }
}
