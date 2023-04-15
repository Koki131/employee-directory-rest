package com.employeedirectory.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.repository.ProspectRepository;

import jakarta.transaction.Transactional;

@Service
public class ProspectServiceImpl implements ProspectService {

	@Autowired
	private ProspectRepository prospectRepository;
	
	@Override
	@Transactional
	public Prospect getProspect(Long prospectId) {
		return prospectRepository.getReferenceById(prospectId);
	}

	@Override
	@Transactional
	public void saveProspect(Prospect prospect) {
		prospectRepository.save(prospect);
	}

	@Override
	@Transactional
	public void deleteProspect(Long prospectId) {
		prospectRepository.deleteById(prospectId);
	}

	@Override
	@Transactional
	public List<Prospect> findAllProspectsByKeyword(int employeeId, String keyword) {
		
		return prospectRepository.findAllByKeyword(employeeId, keyword);
		
	}

	@Override
	public List<Prospect> findAll() {

		return prospectRepository.findAll();
	}

}
