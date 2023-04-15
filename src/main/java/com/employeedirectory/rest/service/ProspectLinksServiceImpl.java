package com.employeedirectory.rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeedirectory.rest.entity.ProspectLinks;
import com.employeedirectory.rest.repository.ProspectLinksRepository;

import jakarta.transaction.Transactional;

@Service
public class ProspectLinksServiceImpl implements ProspectLinksService {

	@Autowired
	private ProspectLinksRepository prospectLinksRepository;
	
	@Override
	@Transactional
	public ProspectLinks getProspectLinks(Long prospectId) {
		return prospectLinksRepository.getReferenceById(prospectId);
	}

	@Override
	@Transactional
	public void saveProspectLinks(ProspectLinks links) {
		prospectLinksRepository.save(links);
	}

	@Override
	@Transactional
	public void deleteProspectLinks(Long linkId) {
		prospectLinksRepository.deleteById(linkId);
	}




}
