package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.dto.request.ProductRevenueRequest;
import com.poly.quanlybanhang.dto.response.OrderDetailResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.report.*;

import com.poly.quanlybanhang.repository.OrderDetailRepository;
import com.poly.quanlybanhang.repository.OrderRepository;
import com.poly.quanlybanhang.service.OrderDetailService;
import com.poly.quanlybanhang.service.ReportRevenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportRevenueServiceImpl implements ReportRevenuService {

    OrderDetailRepository orderDetailRepository;
    OrderRepository orderRepository;
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
    public List<ProductRevenueStatistics> getProductRevenueByAllDates(ProductRevenueRequest request) {
        var dateStart = request.getDateStart();
        var dateEnd = request.getDateEnd();
        var productName = request.getProductName();

        return orderDetailRepository.findProductRevenueByAllDates(dateStart, dateEnd, productName);
    }

    @Override
    public Map<String, Object> getRevenueDataChart(String range) {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate;
        switch (range){
            case "1d": startDate = endDate.minusDays(1);
                break;
            case "7d":
                startDate = endDate.minusDays(7);
                break;
            case "1m":
                startDate = endDate.minusDays(30);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + range);
        }
        List<OrderDetails> orderDetailsList = orderDetailRepository.findByCreateAtBetween(startDate, endDate);
        return generateRevenueData(orderDetailsList, range);
    }

    @Override
    public List<RevenueByGenderAllDay> RevenueByGenderAllDay() {
        return orderRepository.findRevenueByGenderAllDay();
    }

    private Map<String, Object> generateRevenueData(List<OrderDetails> orderDetailsList, String range) {
        Map<String, Object> response = new HashMap<>();
        List<String> labels = new ArrayList<>();
        List<Double> data = new ArrayList<>();

        // Chọn định dạng phù hợp dựa trên range
        DateTimeFormatter formatter;
        String formatPattern;

        switch (range) {
            case "1d":
                formatPattern = "HH:mm";
                break;
            case "7d":
            case "1m":
            default:
                formatPattern = "yyyy-MM-dd";
                break;
        }

        formatter = DateTimeFormatter.ofPattern(formatPattern);

        labels = orderDetailsList.stream()
                .collect(Collectors.groupingBy(od -> od.getCreateAt().format(formatter)))
                .keySet().stream().sorted().collect(Collectors.toList());

        for (String label : labels) {
            double totalRevenue = orderDetailsList.stream()
                    .filter(od -> od.getCreateAt().format(formatter).equals(label))
                    .mapToDouble(od -> od.getQuantity() * od.getPrice())
                    .sum();
            data.add(totalRevenue);
        }

        response.put("labels", labels);
        response.put("data", data);
        return response;
    }


}
