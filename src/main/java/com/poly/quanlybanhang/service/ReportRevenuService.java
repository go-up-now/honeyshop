package com.poly.quanlybanhang.service;

import com.poly.quanlybanhang.dto.request.ProductRevenueRequest;
import com.poly.quanlybanhang.dto.response.OrderDetailResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.report.CustomerStatistics;
import com.poly.quanlybanhang.report.EmployeePerformance;
import com.poly.quanlybanhang.report.ProductRevenueStatistics;
import com.poly.quanlybanhang.report.SellHistory;
import org.springframework.boot.autoconfigure.mail.MailProperties;

import java.util.List;
import java.util.Map;

public interface ReportRevenuService {
    public List<SellHistory> getAllRevenueReport();
    public List<CustomerStatistics> getTopCustomersByOrderValue();
//    public List<CustomerStatistics> getTopCustomersByOrderCount();
    public List<EmployeePerformance> getEmployeePerformanceSummary();

    public List<ProductRevenueStatistics> getProductRevenueByAllDates(ProductRevenueRequest request);

    Map<String,Object> getRevenueDataChart(String range);

}
