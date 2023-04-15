package com.employeedirectory.rest.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employeedirectory.rest.email.EmailRequest;
import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.ProspectService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@RestController
@RequestMapping("/api/v1")
public class ContactController {

	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	private EmployeeService employeeService;
	
	@Autowired
	private ProspectService prospectService;
	
	@PostMapping("/employees/contact/sendEmail/employeeId={employeeId}")
	public ResponseEntity<String> sendEmployeeEmail(@PathVariable int employeeId, @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
		
		Employee employee = employeeService.findById(employeeId);
		
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);

	    String mailSubject = emailRequest.getFullName() + " has sent a message";
	    String mailContent = "<p><b>Sender Name:</b> " + emailRequest.getFullName() + "</p>";
	    mailContent += "<p><b>Subject:</b> " + emailRequest.getSubject() + "</p>";
	    mailContent += "<p><b>Content:</b><br><br> " + emailRequest.getContent() + "</p><br><br>";
	    mailContent += "<hr><img style='margin-bottom: 20px; width: 20%; height: 20%;'src='cid:logo-generic' />";
	    
	    // Enter same e-mail as application.properties
	    helper.setFrom("your-outlook-email@hotmail.com", "Big Business");
	    helper.setTo(employee.getEmail());
	    helper.setSubject(mailSubject);
	    helper.setText(mailContent, true);

	    ClassPathResource classPath = new ClassPathResource("/static/images/logo-generic.png");
	    helper.addInline("logo-generic", classPath);


	    mailSender.send(message);

	    return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
	}
	
	@PostMapping("/prospects/contact/sendEmail/prospectId={prospectId}")
	public ResponseEntity<String> sendProspectEmail(@PathVariable Long prospectId, @RequestBody EmailRequest emailRequest) throws MessagingException, UnsupportedEncodingException {
		
		Prospect prospect = prospectService.getProspect(prospectId);
		
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message, true);

	    String mailSubject = emailRequest.getFullName() + " has sent a message";
	    String mailContent = "<p><b>Sender Name:</b> " + emailRequest.getFullName() + "</p>";
	    mailContent += "<p><b>Subject:</b> " + emailRequest.getSubject() + "</p>";
	    mailContent += "<p><b>Content:</b><br><br> " + emailRequest.getContent() + "</p><br><br>";
	    mailContent += "<hr><img style='margin-bottom: 20px; width: 20%; height: 20%;'src='cid:logo-generic' />";
	    
	    // Enter same e-mail as application.properties
	    helper.setFrom("your-outlook-email@hotmail.com", "Big Business");
	    helper.setTo(prospect.getEmail());
	    helper.setSubject(mailSubject);
	    helper.setText(mailContent, true);

	    ClassPathResource classPath = new ClassPathResource("/static/images/logo-generic.png");
	    helper.addInline("logo-generic", classPath);


	    mailSender.send(message);

	    return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
	}

	
}
