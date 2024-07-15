package com.poly.quanlybanhang.service;

import com.poly.quanlybanhang.dto.request.ProductRevenueRequest;
import com.poly.quanlybanhang.dto.response.OrderDetailResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.report.*;
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

    public List<RevenueByGenderAllDay> RevenueByGenderAllDay();

    List<RevenueProfitCosts> getRevenueProfitCosts();

    List<QuantityByProduct> getQuantityByProduct();

}
