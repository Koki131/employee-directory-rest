package com.employeedirectory.rest.controller;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.entity.Prospect;
import com.employeedirectory.rest.entity.ProspectLinks;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.ProspectLinksService;
import com.employeedirectory.rest.service.ProspectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ProspectController.class)
class ProspectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProspectService prospectService;

    @MockBean
    private ProspectLinksService prospectLinksService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ExceptionUtility exceptionUtility;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetAllProspects() throws Exception {

        int empployeeId = 1;

        Employee employee = new Employee(empployeeId, "John");
        List<Prospect> prospects = List.of(new Prospect("John Smith", "test123@gmail.com"));
        employee.setProspects(prospects);

        when(employeeService.findById(empployeeId)).thenReturn(employee);

        mockMvc.perform(get("/api/v1/prospects/employeeId={employeeId}", empployeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").exists());

    }

    @Test
    void testGetAllProspectsByKeyword() throws Exception {

        int employeeId = 1;
        String keyword = "John";

        List<Prospect> prospects = List.of(new Prospect("John Smith", "test123@gmail.com"));

        when(prospectService.findAllProspectsByKeyword(employeeId, keyword)).thenReturn(prospects);

        mockMvc.perform(get("/api/v1/prospects/search/employeeId={employeeId}&keyword={keyword}", employeeId, keyword))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").exists());

    }

    @Test
    void testGetSingleProspect() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);

        mockMvc.perform(get("/api/v1/prospects/get/prospectId={prospectId}", prospectId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName").exists());

    }

    @Test
    void testAddProspect() throws Exception {

        int employeeId = 1;

        Employee employee = new Employee(1, "John");
        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        employee.addProspect(new Prospect("Jane Smith", "test321@gmail.com"));
        prospect.setEmployee(employee);

        when(employeeService.findById(employeeId)).thenReturn(employee);

        List<Prospect> prospects = employee.getProspects();

        prospectService.saveProspect(prospect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/prospects/save/employeeId={employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prospect));



        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void testUpdateProspect() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/prospects/update/prospectId={prospectId}", prospectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prospect));

        prospectService.saveProspect(prospect);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void testDeleteProspect() throws Exception {

        long prospectId = 1L;


        prospectService.deleteProspect(prospectId);


        mockMvc.perform(delete("/api/v1/prospects/delete/prospectId={prospectId}", prospectId))
                .andExpect(status().isOk());


    }

    @Test
    void testGetProspectLinks() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");
        ProspectLinks links = new ProspectLinks("instagram.com", "linkedin.com", "facebook.com");
        prospect.setProspectLinks(links);
        links.setProspect(prospect);

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);


        mockMvc.perform(get("/api/v1/prospects/prospectLinks/get/prospectId={prospectId}", prospectId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.instagram").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.linkedIn").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.facebook").exists());

    }

    @Test
    void testSaveProspectLinks() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");
        ProspectLinks links = new ProspectLinks("instagram.com", "linkedin.com", "facebook.com");
        prospect.setProspectLinks(links);
        links.setProspect(prospect);

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/prospects/prospectLinks/save/prospectId={prospectId}", prospectId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prospect));

        prospectLinksService.saveProspectLinks(links);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateProspectLinks() throws Exception {

        long prospectId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");
        ProspectLinks prospectLinks = new ProspectLinks("instagram.com", "linkedin.com", "facebook.com");


        ProspectLinks newLinks = new ProspectLinks("instagram.com", "linkedin.com", "facebook.com");

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);

        prospectLinks.setLinkedIn(newLinks.getLinkedIn());
        prospectLinks.setFacebook(newLinks.getFacebook());
        prospectLinks.setInstagram(newLinks.getInstagram());

        prospect.setProspectLinks(prospectLinks);
        prospectLinks.setProspect(prospect);

        prospectLinksService.saveProspectLinks(newLinks);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/prospects/prospectLinks/update/prospectId={prospectId}", prospectId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prospectLinks));


        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void testDeleteProspectLinks() throws Exception {

        long prospectId = 1L;
        long linkId = 1L;

        Prospect prospect = new Prospect("John Smith", "test123@gmail.com");

        when(prospectService.getProspect(prospectId)).thenReturn(prospect);

        prospect.setProspectLinks(null);

        prospectLinksService.deleteProspectLinks(linkId);

        mockMvc.perform(delete("/api/v1/prospects/prospectLinks/delete/prospectId={prospectId}&linkId={linkId}", prospectId, linkId))
                .andExpect(status().isOk());


    }
}