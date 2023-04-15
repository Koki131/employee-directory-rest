package com.employeedirectory.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.employeedirectory.rest.entity.MonthlySales;



public interface MonthlySalesRepository extends JpaRepository<MonthlySales, Long> {

	@Query(value = "SELECT s FROM MonthlySales s WHERE s.employeeId =:employee_id")
	List<MonthlySales> findSalesById(@Param("employee_id") int employeeId);
}
