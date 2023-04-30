package com.employeedirectory.rest.service;

import com.employeedirectory.rest.entity.Employee;
import com.employeedirectory.rest.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



class EmployeeServiceImplTest {

    @Mock
    @Autowired
    private EmployeeService employeeServiceMock;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {

        List<Employee> expectedEmployees = List.of(new Employee("John", "Smith", "test123@gmail.com"));

        when(employeeServiceMock.findAll()).thenReturn(expectedEmployees);

        List<Employee> actualEmployees = employeeServiceMock.findAll();

        assertEquals(expectedEmployees, actualEmployees);

    }

    @Test
    void testSave() {

        Employee employee = new Employee("John", "Smith", "test123@gmail.com");

        when(employeeServiceMock.save(employee)).thenReturn(employee);

        Employee actualEmployee = employeeServiceMock.save(employee);

        assertEquals(employee, actualEmployee);

        verify(employeeServiceMock).save(employee);

    }

    @Test
    void testDelete() {

        employeeServiceMock.delete(0);

        verify(employeeServiceMock).delete(0);

    }

    @Test
    void testFindById() {

        Employee employee = new Employee("John", "Smith", "test123@gmail.com");

        when(employeeServiceMock.findById(0)).thenReturn(employee);

        Employee actualEmployee = employeeServiceMock.findById(0);

        assertEquals(employee, actualEmployee);

    }

    @Test
    void testFindPageWithAllParameters() {

        int pageNum = 1;
        String sortField = "firstName";
        String keyword = "john";
        String sortDir = "asc";

        Pageable pageable = PageRequest.of(pageNum - 1, 5, Sort.by(sortField).ascending());

        List<Employee> expectedEmployees = List.of(new Employee("John", "Smith", "test123@gmail.com"));

        Page<Employee> page = new PageImpl<>(expectedEmployees, pageable, 5);

        when(employeeServiceMock.findPage(pageNum, sortField, sortDir, keyword)).thenReturn(page);

        Page<Employee> actualPage = employeeServiceMock.findPage(pageNum, sortField, sortDir, keyword);

        assertEquals(page, actualPage);

    }

    @Test
    void testFindPageWithPageNumAndKeyword() {

        int pageNum = 1;
        String keyword = "John";

        Pageable pageable = PageRequest.of(pageNum - 1, 5);

        List<Employee> expectedEmployees = List.of(new Employee("John", "Smith", "test123@gmail.com"));

        Page<Employee> page = new PageImpl<>(expectedEmployees, pageable, 5);

        when(employeeServiceMock.findPage(pageNum, keyword)).thenReturn(page);

        Page<Employee> actualPage = employeeServiceMock.findPage(pageNum, keyword);

        assertEquals(page, actualPage);



    }
}