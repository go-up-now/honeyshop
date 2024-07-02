package com.poly.quanlybanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bao-cao")
public class ReportController {
    @GetMapping("/doanh-thu")
    public String getRevenues(){
        return "/admin/reports/revenues";
    }

    @GetMapping("/hieu-suat-ban-hang")
    public String getPerformance(){
        return "/admin/reports/job-performance";
    }

    @GetMapping("/khach-hang-mua-nhieu")
    public String getCustomers(){
        return "/admin/reports/customers";
    }
}
