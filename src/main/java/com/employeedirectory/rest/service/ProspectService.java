package com.employeedirectory.rest.service;

import java.util.List;

import com.employeedirectory.rest.entity.Prospect;

public interface ProspectService {

	Prospect getProspect(Long prospectId);
	
	void saveProspect(Prospect prospect);
	
	void deleteProspect(Long prospectId);
	
	List<Prospect> findAll();
	
	List<Prospect> findAllProspectsByKeyword(int employeeId, String keyword);
	
}
