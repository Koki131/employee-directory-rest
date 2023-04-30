package com.employeedirectory.rest.controller;

import com.employeedirectory.rest.entity.*;
import com.employeedirectory.rest.exceptions.ExceptionUtility;
import com.employeedirectory.rest.service.EmployeeService;
import com.employeedirectory.rest.service.SalesService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.apache.logging.log4j.ThreadContext.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(SalesController.class)
class SalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SalesService salesService;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private ExceptionUtility exceptionUtility;

    @Test
    void testAddSale() throws Exception {

        LocalDate date = LocalDate.of(2023, 4, 26);
        BigDecimal amount = BigDecimal.valueOf(1000);
        int employeeId = 1;

        Employee employee = new Employee(employeeId, "John");
        Sales sale = new Sales(date, employee.getFirstName() + " " + employee.getLastName(), amount);

        when(employeeService.findById(employeeId)).thenReturn(employee);

        employee.addSale(sale);
        sale.setEmployee(employee);


        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/sales/save/employeeId={employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale));

        salesService.saveSale(sale);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

    }

    @Test
    void testGetAllSalesWithEmployeeId() throws Exception {

        LocalDate date = LocalDate.of(2023, 4, 26);
        BigDecimal amount = BigDecimal.valueOf(1000);
        int employeeId = 1;

        Employee employee = new Employee(1, "John");
        List<Sales> sales = List.of(new Sales(date, "John Smith", amount));

        employee.setSales(sales);

        when(employeeService.findById(employeeId)).thenReturn(employee);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/get/employeeId={employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].fullName").exists());

    }

    @Test
    void testGetAllSalesWithEmployeeIdByYear() throws Exception {

        LocalDate date = LocalDate.of(2023, 4, 26);
        int year = date.getYear();
        BigDecimal amount = BigDecimal.valueOf(1000);
        int employeeId = 1;

        Employee employee = new Employee(1, "John");
        List<Sales> sales = List.of(new Sales(date, "John Smith", amount), new Sales(date, "Peter Smith", amount));

        when(employeeService.findById(employeeId)).thenReturn(employee);

        employee.setSales(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/get/year={year}&employeeId={employeeId}", year, employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date").exists());

    }

    @Test
    void testMonthlySalesWithEmployeeId() throws Exception {

        BigDecimal amount = BigDecimal.valueOf(1000);
        int employeeId = 1;

        List<MonthlySales> sales = List.of(new MonthlySales(26, 4, 2023, amount));

        when(salesService.findAllMonthlyById(employeeId)).thenReturn(sales);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/monthlySales/employeeId={employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSales").exists());

    }

    @Test
    void testMonthlySalesWithEmployeeIdByYear() throws Exception {

        int employeeId = 1;
        int year = 2023;
        BigDecimal amount = BigDecimal.valueOf(1000);

        List<MonthlySales> sales = List.of(new MonthlySales(23, 4, 2023, amount), new MonthlySales(26, 4, 2023, amount));

        when(salesService.findAllMonthlyById(employeeId)).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/monthlySales/year={year}&employeeId={employeeId}", year, employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSales").exists());
    }

    @Test
    void testYearlySalesWithEmployeeId() throws Exception {

        int employeeId = 1;
        BigDecimal amount = BigDecimal.valueOf(1000);

        List<YearlySales> sales = List.of(new YearlySales("John Smith", 4, 2023, amount));

        when(salesService.findAllYearlyById(employeeId)).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/yearlySales/employeeId={employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSales").exists());
    }

    @Test
    void testYearlySalesWithEmployeeIdByYear() throws Exception {

        int employeeId = 1;
        int year = 2023;
        BigDecimal amount = BigDecimal.valueOf(1000);

        List<YearlySales> sales = List.of(new YearlySales("John Smith", 23, year, amount), new YearlySales("Jane Smith", 12, year, amount));

        when(salesService.findAllYearlyById(employeeId)).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/yearlySales/year={year}&employeeId={employeeId}", year, employeeId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSales").exists());
    }

    @Test
    void testTotalSales() throws Exception {

        int year = 2023;
        BigDecimal amount = BigDecimal.valueOf(1000);

        List<TotalYearlySales> sales = List.of(new TotalYearlySales("John Smith", year, amount));

        when(salesService.findAll()).thenReturn(sales);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/sales/totalSales/year={year}", year))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].totalSales").exists());
    }

    @Test
    void testUpdateSale() throws Exception {

        long saleId = 1L;

        LocalDate date = LocalDate.of(2023, 4, 26);
        BigDecimal amount = BigDecimal.valueOf(1000);

        Employee employee = new Employee(1, "Jake");
        Sales sale = new Sales(date, "John Smith", amount);

        when(salesService.getSale(saleId)).thenReturn(sale);

        sale.setEmployee(employee);
        sale.setAmount(amount);
        sale.setDate(date);
        sale.setFullName("Joshua Smith");

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/sales/update/saleId={saleId}", saleId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sale));

        salesService.saveSale(sale);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteSale() throws Exception {

        long saleId = 1L;

        salesService.deleteSale(saleId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/sales/delete/saleId={saleId}", saleId))
                .andExpect(status().isOk());

    }
}