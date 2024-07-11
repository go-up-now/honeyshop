package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.service.OrderService;
import com.poly.quanlybanhang.statistical.AgeOfProductConsumption;
import com.poly.quanlybanhang.statistical.GenderOfProductConsumption;
import com.poly.quanlybanhang.statistical.SalesTimeFrame;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/statistical")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class StatisticalControllerApi {
    OrderService orderService;

    @GetMapping("/age-of-product-consumption")
    public ApiResponse<List<AgeOfProductConsumption>> getAgeOfProductConsumption(){
        return ApiResponse.<List<AgeOfProductConsumption>>builder()
                .code(1000)
                .data(orderService.getAgeOfProductConsumption())
                .build();
    }

    @GetMapping("/gender-of-product-consumption")
    public ApiResponse<List<GenderOfProductConsumption>> getGenderOfProductConsumption(){
        return ApiResponse.<List<GenderOfProductConsumption>>builder()
                .code(1000)
                .data(orderService.getGenderOfProductConsumption())
                .build();
    }

    @GetMapping("/sales-time-frame")
        public ApiResponse<List<SalesTimeFrame>> getSalesTimeFrame(){
        return ApiResponse.<List<SalesTimeFrame>>builder()
                .code(1000)
                .data(orderService.getSalesTimeFrame())
                .build();
    }
}
