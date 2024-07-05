package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.report.ReportRevenue;
import com.poly.quanlybanhang.service.ReportRevenuService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/report")
public class ReportControllerApi {
    ReportRevenuService reportService;

    @GetMapping
    public List<ReportRevenue> getRevenueByProduct() {
        return reportService.getAllRevenueReport();
    }
}
