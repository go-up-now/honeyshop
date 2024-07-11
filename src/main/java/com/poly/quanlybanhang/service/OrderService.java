package com.poly.quanlybanhang.service;

import com.poly.quanlybanhang.dto.request.OrderRequest;
import com.poly.quanlybanhang.dto.request.ProductRequest;
import com.poly.quanlybanhang.dto.response.OrderResponse;
import com.poly.quanlybanhang.dto.response.ProductResponse;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.entity.Products;
import com.poly.quanlybanhang.statistical.AgeOfProductConsumption;
import com.poly.quanlybanhang.statistical.GenderOfProductConsumption;
import com.poly.quanlybanhang.statistical.SalesTimeFrame;

import java.util.Date;
import java.util.List;

public interface OrderService {
    OrderResponse create(OrderRequest request);
    OrderResponse update(OrderRequest request, String id);
    void delete(String id);
    List<OrderResponse> getAll();
    Orders getOne(String id);
    OrderResponse getOrder(String id);
    Orders getOrderByPhone(String phone);
    List<AgeOfProductConsumption> getAgeOfProductConsumption();
    List<GenderOfProductConsumption> getGenderOfProductConsumption();
    List<SalesTimeFrame> getSalesTimeFrame();
}
