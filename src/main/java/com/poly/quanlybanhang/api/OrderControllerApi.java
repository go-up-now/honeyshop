package com.poly.quanlybanhang.api;

import com.poly.quanlybanhang.dto.request.OrderRequest;
import com.poly.quanlybanhang.dto.response.ApiResponse;
import com.poly.quanlybanhang.dto.response.OrderResponse;
import com.poly.quanlybanhang.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderControllerApi {
    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> create(@RequestBody OrderRequest request){
        return ApiResponse.<OrderResponse>builder()
                .code(1000)
                .data(orderService.create(request))
                .build();
    }
}
