package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.request.OrderRequest;
import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.dto.response.OrderResponse;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.report.DashboardStatistics;
import com.poly.quanlybanhang.service.OrderDetailService;
import com.poly.quanlybanhang.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api")
public class DashboardApi {
    OrderDetailService orderDetailServiceService;

    @GetMapping("/dashboard")
    public DashboardStatistics getDashboardStatistics() {
        Double totalRevenue = orderDetailServiceService.getTotalRevenue();
        Long totalQuantity = orderDetailServiceService.getTotalQuantityProduct();
        Long totalCustomers = orderDetailServiceService.getTotalCustomers();
        Long totalProfits = orderDetailServiceService.getTotalProfit();
        return new DashboardStatistics(totalRevenue, totalQuantity, totalCustomers,totalProfits);
    }
}
