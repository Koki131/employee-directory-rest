package com.employeedirectory.rest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employeedirectory.rest.entity.Prospect;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Long> {

	
	
	@Query(value = "SELECT p FROM Prospect p WHERE p.employee.id=:employee_id "
			+ "AND p.fullName LIKE %:keyword% OR p.employee.id=:employee_id AND p.email LIKE %:keyword%")
	List<Prospect> findAllByKeyword(@Param("employee_id") int employeeId, @Param("keyword") String keyword);
	
}
