package com.employeedirectory.rest.controller;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.exceptions.NotFoundException;
import com.employeedirectory.rest.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ExceptionUtility exceptionUtility;


    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testListAll() throws Exception {

        int id = 1;

        MockHttpServletRequestBuilder requestBuilder = get("/api/v1/employees")
                .accept(MediaType.APPLICATION_JSON);

        List<Employee> employees = List.of(new Employee(id, "John"));

        when(employeeService.findAll()).thenReturn(employees);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").exists());

    }

    @Test
    void testFindEmployeesWithSortFieldAndSortDir() throws Exception {

        int pageNum = 1;
        String sortField = "firstName";
        String sortDir = "asc";
        String keyword = "John";
        List<Employee> expectedEmployees = List.of(new Employee(1, "John"));

        Page<Employee> page = new PageImpl<>(expectedEmployees);
        when(employeeService.findPage(pageNum, sortField, sortDir, keyword)).thenReturn(page);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees/find/pageNum={pageNum}&sortField={sortField}&sortDir={sortDir}&keyword={keyword}",
                pageNum, sortField, sortDir, keyword);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedEmployees.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(expectedEmployees.get(0).getFirstName()));
    }


    @Test
    void testFindEmployeesWithoutSortFieldAndSortDir() throws Exception {

        int pageNum = 1;
        String keyword = "John";

        List<Employee> expectedEmployees = List.of(new Employee(1, "John"));

        Page<Employee> page = new PageImpl<>(expectedEmployees);

        when(employeeService.findPage(pageNum, keyword)).thenReturn(page);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees/search/pageNum={pageNum}&keyword={keyword}",
                pageNum, keyword);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(expectedEmployees.size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value(expectedEmployees.get(0).getFirstName()));

    }

    @Test
    void testGetEmployeeWithoutException() throws Exception {

        int employeeId = 1;

        Employee employee = new Employee(employeeId, "John");

        when(employeeService.findById(employeeId)).thenReturn(employee);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/employees/get/employeeId={employeeId}", employeeId);

        Employee actualEmployee = employeeService.findById(employeeId);

        assertEquals(employee, actualEmployee);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(employeeId));


    }

    @Test
    void testEmployeeNotFoundException() throws Exception {

        int employeeId = 1000;

        when(employeeService.findById(employeeId)).thenThrow(new NotFoundException("Employee with id " + employeeId + " not found"));


        mockMvc.perform(get("/api/v1/employees/get/employeeId={employeeId}", employeeId))
                .andExpect(status().isNotFound());


        verify(exceptionUtility).employeeNotFound(employeeId);


    }

    @Test
    void testSaveEmployee() throws Exception {

        Employee employee = new Employee(1, "John");


        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/employees/save")
                        .content(objectMapper.writeValueAsString(employee))
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.content().contentType("application/json"));

    }

    @Test
    void testUploadImage() throws Exception {

        int employeeId = 1;
        Employee employee = new Employee(employeeId, "John");

        when(employeeService.findById(employeeId)).thenReturn(employee);
        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        MockMultipartFile multipartFile = new MockMultipartFile("fileImage", "test.jpg", "multipart/form-data", "test".getBytes());


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.multipart("/api/v1/employees/uploadImage/employeeId={employeeId}", employeeId)
                .file("fileImage", multipartFile.getBytes())
                .contentType(MediaType.MULTIPART_FORM_DATA);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(employeeService).save(any(Employee.class));

    }

    @Test
    void testUpdateEmployee() throws Exception {

        int employeeId = 1;

        Employee employee = new Employee(employeeId, "John");

        when(employeeService.findById(employeeId)).thenReturn(employee);
        when(employeeService.save(any(Employee.class))).thenReturn(employee);

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/employees/update/employeeId={employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee));

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(employeeService).save(any(Employee.class));


    }

    @Test
    void testDeleteEmployee() throws Exception {

        int employeeId = 1;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/employees/delete/employeeId={employeeId}", employeeId))
                .andExpect(status().isOk());

        verify(employeeService).delete(employeeId);

    }
}