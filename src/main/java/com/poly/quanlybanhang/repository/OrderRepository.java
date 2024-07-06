package com.poly.quanlybanhang.repository;

import com.poly.quanlybanhang.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    Optional<Orders> findTopByPhoneOrderByCreateAtDesc(String phone);
}
