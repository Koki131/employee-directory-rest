package com.employeedirectory.rest.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;



@Entity
@Table(name = "yearly_sales")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class YearlySales implements Comparable<YearlySales>{

	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name = "employee_id")
	private int employeeId;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "month")
	private int month;
	
	@Column(name = "year")
	private int year;
	
	@Column(name = "total_sales")
	private BigDecimal totalSales;
	
	
	
	public YearlySales() {
		
	}


	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BigDecimal getTotalSales() {
		return totalSales;
	}

	public void setTotalSales(BigDecimal totalSales) {
		this.totalSales = totalSales;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Override
	public String toString() {
		return "YearlySales [id=" + id + ", employeeId=" + employeeId + ", month=" + month + ", year=" + year
				+ ", totalSales=" + totalSales + "]";
	}

	@Override
	public int compareTo(YearlySales o) {
		
		if (this.month == o.getMonth()) {
			return 0;
		} else if (this.month > o.getMonth()) {
			return 1;
		} else {
			return -1;
		}
	}
	
	
}
