package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.dto.response.OrderDetailResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.report.CustomerStatistics;
import com.poly.quanlybanhang.report.EmployeePerformance;
import com.poly.quanlybanhang.report.ProductRevenueStatistics;

import com.poly.quanlybanhang.report.SellHistory;
import com.poly.quanlybanhang.repository.OrderDetailRepository;
import com.poly.quanlybanhang.service.ReportRevenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportRevenueServiceImpl implements ReportRevenuService {

    OrderDetailRepository orderDetailRepository;

    @Override
    public List<SellHistory> getAllRevenueReport() {
        return orderDetailRepository.findRevenueReport();
    }

    @Override
    public List<CustomerStatistics> getTopCustomersByOrderValue() {
        return orderDetailRepository.findTopCustomersByOrderValue();
    }

//    @Override
//    public List<CustomerStatistics> getTopCustomersByOrderCount() {
//        return orderDetailRepository.findTopCustomersByOrderCount();
//    }

    @Override
    public List<EmployeePerformance> getEmployeePerformanceSummary() {

        return orderDetailRepository.getEmployeePerformanceSummary();
    }

    @Override
    public List<ProductRevenueStatistics> getProductRevenueByAllDates() {
        return orderDetailRepository.findProductRevenueByAllDates();
    }





}
