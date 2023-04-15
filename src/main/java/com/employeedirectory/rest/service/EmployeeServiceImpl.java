package com.employeedirectory.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.repository.EmployeeRepository;

import jakarta.transaction.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	@Transactional
	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	@Override
	@Transactional
	public Employee save(Employee employee) {
		employeeRepository.save(employee);
		
		return employee;
	}

	@Override
	@Transactional
	public void delete(int employeeId) {
		employeeRepository.deleteById(employeeId);
	}

	@Override
	@Transactional
	public Employee findById(int employeeId) {
		
		Employee employee = employeeRepository.getReferenceById(employeeId);
		
		return employee;
		
	}
	
	@Override
	@Transactional
	public Page<Employee> findPage(int pageNum, String sortField, String sortDir, String keyword) {
		
		Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
		
		Pageable pageable = null;
		
		if (pageNum > 0) {
			pageable = PageRequest.of(pageNum - 1, 5, sort);
		}
		
		if (keyword != null) {
			return employeeRepository.getByKeyword(keyword, pageable);
		}
		
		return employeeRepository.findAll(pageable);
		
	}

	@Override
	@Transactional
	public Page<Employee> findPage(int pageNum, String keyword) {
		
		Pageable pageable = null;
		
		if (pageNum > 0) {
			pageable = PageRequest.of(pageNum - 1, 5);
		}
		
		if (keyword != null) {
			return employeeRepository.getByKeyword(keyword, pageable);
		}
		
		return employeeRepository.findAll(pageable);
	}

}
