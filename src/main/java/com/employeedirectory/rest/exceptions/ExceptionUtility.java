package com.employeedirectory.rest.exceptions;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.entity.Sales;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.ProspectService;
import com.employeedirectory.rest.service.SalesService;

@Component
public class ExceptionUtility {

	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProspectService prospectService;
	
	@Autowired
	private SalesService salesService;

	
	
	public void employeeNotFound(int employeeId) {
		
		List<Employee> employees = employeeService.findAll();
		
		if (!employees.contains(employeeService.findById(employeeId))) {
			throw new NotFoundException("Employee with the id " + employeeId + " doesn't exist");
		}
		
		
	}
	
	public void prospectNotFound(Long prospectId) {
		
		List<Prospect> prospects = prospectService.findAll();
		
		if (!prospects.contains(prospectService.getProspect(prospectId))) {
			throw new NotFoundException("Prospect with the id " + prospectId + " doesn't exist");
		}
		
	}
	
	public void saleNotFound(Long saleId) {
		
		List<Sales> sales = salesService.getSales();
		
		if (!sales.contains(salesService.getSale(saleId))) {
			throw new NotFoundException("Sale with the id " + saleId + " doesn't exist");
		}
		
	}

}
