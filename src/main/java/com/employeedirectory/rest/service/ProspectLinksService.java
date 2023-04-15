package com.employeedirectory.rest.service;

import com.employeedirectory.rest.entity.ProspectLinks;

public interface ProspectLinksService {

	ProspectLinks getProspectLinks(Long prospectId);
	
	void saveProspectLinks(ProspectLinks links);
	
	void deleteProspectLinks(Long linkId);

}
