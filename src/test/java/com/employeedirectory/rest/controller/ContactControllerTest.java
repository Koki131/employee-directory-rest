package com.employeedirectory.rest.controller;

import com.employeedirectory.rest.email.EmailRequest;
import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.ProspectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.Session;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JavaMailSender mailSender;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ProspectService prospectService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSendEmployeeEmail() throws Exception {

        int employeeId = 1;

        Employee employee = new Employee(employeeId, "John", "Smith", "test123@gmail.com");
        EmailRequest email = new EmailRequest("John Smith", "test", "test123@gmail.com", "test");

        when(employeeService.findById(employeeId)).thenReturn(employee);

        MimeMessage message = new MimeMessage((Session) null);

        when(mailSender.createMimeMessage()).thenReturn(message);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/employees/contact/sendEmail/employeeId={employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(email));

        mailSender.send(message);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());


    }

    @Test
    void testSendProspectEmail() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");
        EmailRequest email = new EmailRequest("John Smith", "test", "test123@gmail.com", "test");

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);

        MimeMessage message = new MimeMessage((Session) null);

        when(mailSender.createMimeMessage()).thenReturn(message);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/prospects/contact/sendEmail/prospectId={prospectId}", prospectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(email));

        mailSender.send(message);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());


    }
}