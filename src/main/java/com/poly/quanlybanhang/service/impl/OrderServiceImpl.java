package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.dto.request.OrderDetailRequest;
import com.poly.quanlybanhang.dto.request.OrderRequest;
import com.poly.quanlybanhang.dto.response.OrderResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.entity.Products;
import com.poly.quanlybanhang.entity.Users;
import com.poly.quanlybanhang.exception.AppException;
import com.poly.quanlybanhang.exception.ErrorCode;
import com.poly.quanlybanhang.mapper.OrderMapper;
import com.poly.quanlybanhang.repository.OrderDetailRepository;
import com.poly.quanlybanhang.repository.OrderRepository;
import com.poly.quanlybanhang.repository.ProductRepository;
import com.poly.quanlybanhang.service.OrderService;
import com.poly.quanlybanhang.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    UserService userService;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request) {
//        Users user = userService.getOne("b8d9c186-7201-4641-8f1e-f249504238e1");
//
//        Orders orders = orderMapper.toOrder(request);
//        orders.setUser(user);
//        orders.setCreateAt(LocalDateTime.now());
//
//        return orderMapper.toOrderResponse(orderRepository.save(orders));

        Orders order = new Orders();
        order.setFullname(request.getFullname());
        order.setPhone(request.getPhone());
        order.setTotalAmount(request.getTotalAmount());
        order.setStatus(request.getStatus());
        order.setCreateAt(LocalDateTime.now());

        List<OrderDetails> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetail : request.getOrderDetails()) {
            Products product = productRepository.findById(orderDetail.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            OrderDetails od = new OrderDetails();
            od.setOrder(order);
            od.setProduct(product);
            od.setQuantity(orderDetail.getQuantity());
            od.setPrice(orderDetail.getPrice());
            od.setCreateAt(LocalDateTime.now());
//            orderDetails.add(od);
            orderDetailRepository.save(od);
        }

//        order.setOrderDetails(orderDetails);
        return orderMapper.toOrderResponse(orderRepository.save(order));
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
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public Orders getOne(String id) {
        return null;
    }

    @Override
    public OrderResponse getOrder(String id) {
        return orderMapper.toOrderResponse(orderRepository.findById(id).orElseThrow(() ->
                new AppException(ErrorCode.ORDER_NOT_FOUND)));
    }

    @Override
    public Orders getOrderByPhone(String phone) {
        return orderRepository.findTopByPhoneOrderByCreateAtDesc(phone).orElseThrow(() ->
                new AppException(ErrorCode.ORDER_NOT_FOUND));
    }
}
