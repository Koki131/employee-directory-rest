package com.employeedirectory.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeedirectory.rest.entity.TotalYearlySales;
import com.employeedirectory.rest.entity.YearlySales;



@Repository
public interface YearlySalesRepository extends JpaRepository<YearlySales, Integer> {

	
	@Query(value = "SELECT s FROM YearlySales s WHERE s.employeeId =:employee_id")
	List<YearlySales> findSalesById(@Param("employee_id") int employeeId);
	
}
