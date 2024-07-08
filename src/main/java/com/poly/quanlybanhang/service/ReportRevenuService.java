package com.poly.quanlybanhang.service;

import com.poly.quanlybanhang.report.CustomerStatistics;
import com.poly.quanlybanhang.report.EmployeePerformance;
import com.poly.quanlybanhang.report.ProductRevenueStatistics;
import com.poly.quanlybanhang.report.SellHistory;

import java.util.List;

public interface ReportRevenuService {
    public List<SellHistory> getAllRevenueReport();
    public List<CustomerStatistics> getTopCustomersByOrderValue();
    public List<CustomerStatistics> getTopCustomersByOrderCount();
    public List<EmployeePerformance> getEmployeePerformanceSummary();

    public List<ProductRevenueStatistics> getProductRevenueByAllDates();

}
