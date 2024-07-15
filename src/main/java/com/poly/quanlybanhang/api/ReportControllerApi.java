package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.request.ProductRevenueRequest;
import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.report.*;

import com.poly.quanlybanhang.service.ReportRevenuService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportControllerApi {
    ReportRevenuService reportService;

    @GetMapping
    public List<SellHistory> getRevenueByProduct() {

        return reportService.getAllRevenueReport();

    }

    @GetMapping("/top-by-value")
    public List<CustomerStatistics> getTopCustomersByOrderValue() {
        return reportService.getTopCustomersByOrderValue();
    }

//    @GetMapping("/top-by-count")
//    public List<CustomerStatistics> getTopCustomersByOrderCount() {
//        return reportService.getTopCustomersByOrderCount();
//    }

    @GetMapping("/employee-performance")
    public List<EmployeePerformance> getEmployeePerformanceSummary() {
        return reportService.getEmployeePerformanceSummary();
    }

    @GetMapping("/revenue-profit-costs")
    public List<RevenueProfitCosts> getRevenueProfitCosts() {
        return reportService.getRevenueProfitCosts();
    }

    @GetMapping("/quantity-by-product")
    public List<QuantityByProduct> QuantityByProduct() {
        return reportService.getQuantityByProduct();
    }

    @PostMapping("/product-revenue")
    public ApiResponse<List<ProductRevenueStatistics>> getRevenueByAllDates(@RequestBody ProductRevenueRequest request) {

        return ApiResponse.<List<ProductRevenueStatistics>>builder()
                .code(1000)
                .data(reportService.getProductRevenueByAllDates(request))
                .build();
    }

}
