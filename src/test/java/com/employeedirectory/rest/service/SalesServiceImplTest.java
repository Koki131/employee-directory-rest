package com.employeedirectory.rest.service;

import com.employeedirectory.rest.entity.MonthlySales;
import com.employeedirectory.rest.entity.Sales;
import com.employeedirectory.rest.entity.TotalYearlySales;
import com.employeedirectory.rest.entity.YearlySales;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class SalesServiceImplTest {

    @Mock
    @Autowired
    private SalesService salesServiceMock;

    private BigDecimal amount;
    private LocalDate date;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        amount = BigDecimal.valueOf(1000);
        date = LocalDate.of(2023, 4, 23);
    }

    @Test
    void testGetSales() {


        List<Sales> sales = List.of(new Sales(date, "John Smith", amount));

        when(salesServiceMock.getSales()).thenReturn(sales);

        List<Sales> actualSales = salesServiceMock.getSales();

        assertEquals(sales, actualSales);

    }

    @Test
    void testSaveSale() {

        Sales sale = new Sales(date, "John Smith", amount);

        salesServiceMock.saveSale(sale);

        verify(salesServiceMock).saveSale(sale);

    }

    @Test
    void testGetSale() {

        Sales sale = new Sales(date, "John Smith", amount);

        when(salesServiceMock.getSale(0L)).thenReturn(sale);

        Sales actualSale = salesServiceMock.getSale(0L);

        assertEquals(sale, actualSale);

    }

    @Test
    void testDeleteSale() {

        salesServiceMock.deleteSale(0L);

        verify(salesServiceMock).deleteSale(0L);

    }

    @Test
    void testFindAllSalesById() {

        List<Sales> sales = List.of(new Sales(date, "John Smith", amount));

        when(salesServiceMock.findAllSalesById(0)).thenReturn(sales);

        List<Sales> actualSales = salesServiceMock.findAllSalesById(0);

        assertEquals(sales, actualSales);

    }

    @Test
    void testFindAllYearlyById() {

        List<YearlySales> sales = List.of(new YearlySales("John Smith", 4, 2023, amount));

        when(salesServiceMock.findAllYearlyById(0)).thenReturn(sales);

        List<YearlySales> actualSales = salesServiceMock.findAllYearlyById(0);

        assertEquals(sales, actualSales);

    }

    @Test
    void testFindAllMonthlyById() {

        List<MonthlySales> sales = List.of(new MonthlySales(21, 4, 2023, amount));

        when(salesServiceMock.findAllMonthlyById(0)).thenReturn(sales);

        List<MonthlySales> actualSales = salesServiceMock.findAllMonthlyById(0);

        assertEquals(sales, actualSales);

    }

    @Test
    void testFindAll() {

        List<TotalYearlySales> sales = List.of(new TotalYearlySales("John Smith", 2023, amount));

        when(salesServiceMock.findAll()).thenReturn(sales);

        List<TotalYearlySales> actualSales = salesServiceMock.findAll();

        assertEquals(sales, actualSales);

    }

    @Test
    void testFindAllYearly() {

        List<YearlySales> sales = List.of(new YearlySales("John Smith", 4, 2023, amount));

        when(salesServiceMock.findAllYearly()).thenReturn(sales);

        List<YearlySales> actualSales = salesServiceMock.findAllYearly();

        assertEquals(sales, actualSales);

    }
}