package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.dto.request.OrderRequest;
import com.poly.quanlybanhang.dto.response.OrderResponse;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.mapper.OrderMapper;
import com.poly.quanlybanhang.repository.OrderRepository;
import com.poly.quanlybanhang.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;

    @Override
    public OrderResponse create(OrderRequest request) {
        Orders orders = orderMapper.toOrder(request);

        return orderMapper.toOrderResponse(orderRepository.save(orders));
    }

    @Override
    public OrderResponse update(OrderRequest request, String id) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<OrderResponse> getAll() {
        return List.of();
    }

    @Override
    public Orders getOne(String id) {
        return null;
    }

    @Override
    public OrderResponse getProduct(String id) {
        return null;
    }
}
