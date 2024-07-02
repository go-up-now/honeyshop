package com.poly.quanlybanhang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/thong-ke")
public class StatisticalController {
    @GetMapping("/chi-phi-va-nguyen-lieu")
    public String getCostAndMeterial(){
        return "/admin/statistical/cost-material";
    }

    @GetMapping("/chi-phi-va-gia-ban")
    public String getCostAndPrice(){
        return "/admin/statistical/cost-price";
    }
}
