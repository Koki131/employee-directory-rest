package com.employeedirectory.rest.controller;

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
import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.entity.ProspectLinks;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.ProspectLinksService;
import com.employeedirectory.rest.service.ProspectService;

@RestController
@RequestMapping("/api/v1")
public class ProspectController {

	
	@Autowired
	private ProspectService prospectService;
	
	@Autowired
	private ProspectLinksService prospectLinksService;
	
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ExceptionUtility exceptionUtility;
	
	
	@GetMapping("/prospects/employeeId={employeeId}")
	public List<Prospect> getAllProspects(@PathVariable int employeeId) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		
		return employee.getProspects();
	}
	
	@GetMapping("/prospects/search/employeeId={employeeId}&keyword={keyword}")
	public List<Prospect> getAllProspectsByKeyword(@PathVariable int employeeId, @PathVariable String keyword) {
		
		exceptionUtility.employeeNotFound(employeeId);
		
		List<Prospect> prospects = prospectService.findAllProspectsByKeyword(employeeId, keyword);
		
		return prospects;
		
	}
	
	@GetMapping("/prospects/get/prospectId={prospectId}")
	public Prospect getProspect(@PathVariable Long prospectId) {
		
		exceptionUtility.prospectNotFound(prospectId);
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
		
		return prospect;
		
	}
	
	@PostMapping("/prospects/save/employeeId={employeeId}")
	public ResponseEntity<String> addProspect(@PathVariable int employeeId, @RequestBody Prospect prospect) {
		
		
		exceptionUtility.employeeNotFound(employeeId);
		
		Employee employee = employeeService.findById(employeeId);
		
		List<Prospect> prospects = employee.getProspects();
		
		for (Prospect prosp : prospects) {
			if (prosp.getEmail().equals(prospect.getEmail())) {
				return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
			}
		}
		
		employee.addProspect(prospect);
		
		prospect.setEmployee(employee);
		
		prospectService.saveProspect(prospect);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@PutMapping("/prospects/update/prospectId={prospectId}")
	public ResponseEntity<String> updateProspect(@PathVariable Long prospectId, @RequestBody Prospect prospect) {
		
		
		exceptionUtility.prospectNotFound(prospectId);
		
		Prospect oldProspect = prospectService.getProspect(prospectId);
		
		oldProspect.setFullName(prospect.getFullName());
		
		oldProspect.setEmail(prospect.getEmail());
		
		prospectService.saveProspect(oldProspect);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/prospects/delete/prospectId={prospectId}")
	public ResponseEntity<String> deleteProspect(@PathVariable Long prospectId) {

		exceptionUtility.prospectNotFound(prospectId);
		
		prospectService.deleteProspect(prospectId);

		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@GetMapping("/prospects/prospectLinks/get/prospectId={prospectId}")
	public ProspectLinks getProspectLinks(@PathVariable Long prospectId) {
		
		exceptionUtility.prospectNotFound(prospectId);
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
		ProspectLinks links = prospect.getProspectLinks();
		
		return links;
		
	}
	
	@PostMapping("/prospects/prospectLinks/save/prospectId={prospectId}")
	public ResponseEntity<String> saveProspectLinks(@PathVariable Long prospectId, @RequestBody ProspectLinks prospectLinks) {
		
		exceptionUtility.prospectNotFound(prospectId);
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
		prospect.setProspectLinks(prospectLinks);
		
		prospectLinks.setProspect(prospect);
		
		prospectLinksService.saveProspectLinks(prospectLinks);
		
			
		return new ResponseEntity<>("Success", HttpStatus.OK);
	}
	
	
	@PutMapping("/prospects/prospectLinks/update/prospectId={prospectId}")
	public ResponseEntity<String> updateProspectLinks(@PathVariable Long prospectId, @RequestBody ProspectLinks prospectLinks) {
		
		exceptionUtility.prospectNotFound(prospectId);
		
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
		ProspectLinks oldLinks = prospect.getProspectLinks();
		
		if (prospectLinks.getLinkedIn() != null) {
			oldLinks.setLinkedIn(prospectLinks.getLinkedIn());
		} 
		
		if (prospectLinks.getInstagram() != null) {
			oldLinks.setInstagram(prospectLinks.getInstagram());
		}
		
		if (prospectLinks.getFacebook() != null) {
			oldLinks.setFacebook(prospectLinks.getFacebook());
		}
		
		prospect.setProspectLinks(oldLinks);
		
		oldLinks.setProspect(prospect);
		
		prospectLinksService.saveProspectLinks(oldLinks);
		
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	@DeleteMapping("/prospects/prospectLinks/delete/prospectId={prospectId}&linkId={linkId}")
	public ResponseEntity<String> deleteProspectLinks(@PathVariable Long prospectId, @PathVariable Long linkId) {
		
		
		
		exceptionUtility.prospectNotFound(prospectId);
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
		prospect.setProspectLinks(null);
		
		prospectLinksService.deleteProspectLinks(linkId);
		
		return new ResponseEntity<>("Success", HttpStatus.OK);
		
	}
	
	
}
