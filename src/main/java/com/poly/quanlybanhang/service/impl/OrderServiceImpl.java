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
import com.poly.quanlybanhang.repository.UserRepository;
import com.poly.quanlybanhang.service.OrderService;
import com.poly.quanlybanhang.service.UserService;
import com.poly.quanlybanhang.statistical.AgeOfProductConsumption;
import com.poly.quanlybanhang.statistical.GenderOfProductConsumption;
import com.poly.quanlybanhang.statistical.SalesTimeFrame;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    OrderMapper orderMapper;
    OrderRepository orderRepository;
    UserRepository userRepository;
    UserService userService;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderResponse create(OrderRequest request) {
//        Users user = userService.getOne("b8d9c186-7201-4641-8f1e-f249504238e1");
//

//        orders.setUser(user);
//        orders.setCreateAt(LocalDateTime.now());
//
//        return orderMapper.toOrderResponse(orderRepository.save(orders));

        String userId = "05b97912-0b39-487c-9260-b1372afe0d82";
        Users user = userRepository.findById(userId).orElseThrow(() ->
                new AppException(ErrorCode.USER_NOT_FOUND));

        Orders order = orderMapper.toOrder(request);

        order.setUser(user);
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
        orderDetailRepository.deleteById(id);
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

    @Override
    public List<AgeOfProductConsumption> getAgeOfProductConsumption() {
        return orderRepository.getAgeOfProductConsumption();
    }

    @Override
    public List<GenderOfProductConsumption> getGenderOfProductConsumption() {
        return orderRepository.getGenderOfProductConsumption();
    }

    @Override
    public List<SalesTimeFrame> getSalesTimeFrame() {
        return orderRepository.getSalesTimeFrame();
    }
}
