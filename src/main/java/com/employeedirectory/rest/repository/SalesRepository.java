package com.employeedirectory.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeedirectory.rest.entity.Sales;


@Repository
public interface SalesRepository extends JpaRepository<Sales, Long> {

	
	@Query(value = "SELECT s FROM Sales s WHERE s.employee.id =:employee_id")
	List<Sales> findAllSalesById(@Param("employee_id") int employeeId);
    
    
    
}