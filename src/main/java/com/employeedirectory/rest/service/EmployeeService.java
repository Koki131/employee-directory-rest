package com.employeedirectory.rest.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.employeedirectory.rest.entity.Employee;

public interface EmployeeService {

	List<Employee> findAll();
	
	Employee findById(int employeeId);
	
	Employee save(Employee employee);
	
	void delete(int employeeId);	
	
	Page<Employee> findPage(int pageNum, String sortField, String sortDir, String keyword);
	
	Page<Employee> findPage(int pageNum, String keyword);
}
