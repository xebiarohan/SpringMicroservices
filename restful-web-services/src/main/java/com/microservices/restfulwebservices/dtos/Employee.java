package com.microservices.restfulwebservices.dtos;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// @JsonIgnoreProperties(value = {"salary", "phoneNumber"})
@JsonFilter("EmployeeFilter")
public class Employee {
    private String name;
    @JsonIgnore
    private Integer salary;
    private Long phoneNumber;

    public Employee(String name, Integer salary, Long phoneNumber) {
        this.name = name;
        this.salary = salary;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
