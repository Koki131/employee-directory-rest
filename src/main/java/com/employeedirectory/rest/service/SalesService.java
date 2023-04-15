package com.employeedirectory.rest.service;

import java.util.List;

import com.employeedirectory.rest.entity.MonthlySales;
import com.employeedirectory.rest.entity.Sales;
import com.employeedirectory.rest.entity.TotalYearlySales;
import com.employeedirectory.rest.entity.YearlySales;



public interface SalesService {
	

    List<Sales> getSales();
    void saveSale(Sales sale);
    Sales getSale(Long id);
    void deleteSale(Long id);

    List<Sales> findAllSalesById(int employeeId);

    
    List<YearlySales> findAllYearlyById(int employeeId);
    
    List<MonthlySales> findAllMonthlyById(int employeeId);
    
    List<YearlySales> findAllYearly();
    
    List<TotalYearlySales> findAll();
    
    

    

}