package com.employeedirectory.rest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employeedirectory.rest.entity.MonthlySales;
import com.employeedirectory.rest.entity.Sales;
import com.employeedirectory.rest.entity.TotalYearlySales;
import com.employeedirectory.rest.entity.YearlySales;
import com.employeedirectory.rest.repository.MonthlySalesRepository;
import com.employeedirectory.rest.repository.SalesRepository;
import com.employeedirectory.rest.repository.TotalYearlySalesRepository;
import com.employeedirectory.rest.repository.YearlySalesRepository;

@Service
public class SalesServiceImpl implements SalesService {

	@Autowired
	private TotalYearlySalesRepository totalSalesRepo;
	
	@Autowired
	private YearlySalesRepository yearlySalesRepo;
	
	@Autowired
	private MonthlySalesRepository monthlySalesRepo;
	
	@Autowired
	private SalesRepository salesRepo;
	
	@Override
	public List<Sales> getSales() {
		return salesRepo.findAll();
	}

	@Override
	public void saveSale(Sales sale) {
		salesRepo.save(sale);
	}

	@Override
	public Sales getSale(Long id) {
		return salesRepo.getReferenceById(id);
	}

	@Override
	public void deleteSale(Long id) {
		salesRepo.deleteById(id);
	}
	
	@Override
	public List<Sales> findAllSalesById(int employeeId) {
		return salesRepo.findAllSalesById(employeeId);
	}

	@Override
	public List<YearlySales> findAllYearlyById(int employeeId) {
		return yearlySalesRepo.findSalesById(employeeId);
	}

	@Override
	public List<MonthlySales> findAllMonthlyById(int employeeId) {
		return monthlySalesRepo.findSalesById(employeeId);
	}

	@Override
	public List<TotalYearlySales> findAll() {
		return totalSalesRepo.findAll();
	}

	@Override
	public List<YearlySales> findAllYearly() {
		return yearlySalesRepo.findAll();
	}



}
