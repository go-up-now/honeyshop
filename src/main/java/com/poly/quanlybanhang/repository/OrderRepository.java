package com.poly.quanlybanhang.repository;

import com.poly.quanlybanhang.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
}
