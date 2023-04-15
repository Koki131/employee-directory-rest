package com.employeedirectory.rest.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.entity.MonthlySales;
import com.employeedirectory.rest.entity.Sales;
import com.employeedirectory.rest.entity.TotalYearlySales;
import com.employeedirectory.rest.entity.YearlySales;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.SalesService;

@RestController
@RequestMapping("/api/v1")
public class SalesController {

	@Autowired
	private SalesService salesService;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ExceptionUtility exceptionUtility;

	
	@PostMapping("/sales/save/employeeId={employeeId}")
	public ResponseEntity<String> addSale(@PathVariable int employeeId, @RequestBody Sales sale) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		
		employee.addSale(sale);
		
		sale.setEmployee(employee);
		sale.setFullName(employee.getFirstName() + " " + employee.getLastName());
		
		salesService.saveSale(sale);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
	
	}
	
	@GetMapping("/sales/get/employeeId={employeeId}")
	public List<Sales> getSales(@PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		
		return employee.getSales();
		
	}
	
	@GetMapping("/sales/get/year={year}&employeeId={employeeId}")
	public List<Sales> getSales(@PathVariable int year, @PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		
		
		List<Sales> sales = new ArrayList<>();
		
		for (Sales sale : employee.getSales()) {
			
			int y = sale.getDate().getYear();
			
			if (y == year) {
				sales.add(sale);
			}
		}
		
		return sales;
		
	}
	
	@GetMapping("/sales/monthlySales/employeeId={employeeId}")
	public List<MonthlySales> monthlySales(@PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		return salesService.findAllMonthlyById(employeeId);
	}
	
	@GetMapping("/sales/monthlySales/year={year}&employeeId={employeeId}")
	public List<MonthlySales> monthlySales(@PathVariable int year, @PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		List<MonthlySales> allSales = salesService.findAllMonthlyById(employeeId);
		
		List<MonthlySales> sales = new ArrayList<>();
		
		for (MonthlySales sale : allSales) {
			
			if (sale.getYear() == year) {
				sales.add(sale);
			}
		}
		
		return sales;
	}
	

	@GetMapping("/sales/yearlySales/employeeId={employeeId}")
	public List<YearlySales> yearlySales(@PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		return salesService.findAllYearlyById(employeeId);
	}
	
	@GetMapping("/sales/yearlySales/year={year}&employeeId={employeeId}")
	public List<YearlySales> yearlySales(@PathVariable int year, @PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		List<YearlySales> allSales = salesService.findAllYearlyById(employeeId);
		
		List<YearlySales> sales = new ArrayList<>();
		
		for (YearlySales sale : allSales) {
			
			if (sale.getYear() == year) {
				sales.add(sale);
			}
		}
		
		return sales;
		
	}
	
	@GetMapping("/sales/totalSales/year={year}")
	public List<TotalYearlySales> totalSales(@PathVariable int year) {
		
		List<TotalYearlySales> allSales = salesService.findAll();
		
		List<TotalYearlySales> sales = new ArrayList<>();
		
		for (TotalYearlySales sale : allSales) {
			
			if (sale.getYear() == year) {
				sales.add(sale);
			}
		}
		
		return sales;
		
	}
	
	@PutMapping("/sales/update/saleId={saleId}")
	public ResponseEntity<String> updateSale(@PathVariable Long saleId, @RequestBody Sales sale) {
		
		exceptionUtility.saleNotFound(saleId);
		
		Sales originalSale = salesService.getSale(saleId);
		
		Employee employee = salesService.getSale(saleId).getEmployee();
		
		
		originalSale.setAmount(sale.getAmount());
		

		originalSale.setDate(sale.getDate());

		
		
		originalSale.setFullName(employee.getFirstName() + " " + employee.getLastName());
		
		salesService.saveSale(originalSale);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);	
		
	}
	
	
	@DeleteMapping("/sales/delete/saleId={saleId}")
	public ResponseEntity<String> deleteSale(@PathVariable Long saleId) {
		
		exceptionUtility.saleNotFound(saleId);
		
		salesService.deleteSale(saleId);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
}







































