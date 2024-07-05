package com.poly.quanlybanhang.repository;

import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.report.ReportRevenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, String> {
    @Query("SELECT new com.poly.quanlybanhang.report.ReportRevenue(" +
            "u.fullname AS userFullName, " +
            "p.name AS productName, " +
            "p.price AS price, " +
            "SUM(od.quantity * p.price) AS revenue, " +
            "SUM(od.quantity) AS quantity, " +
            "od.order.createAt AS day) " +
            "FROM OrderDetails od " +
            "JOIN od.product p " +
            "JOIN od.order.user u " +
            "GROUP BY u.fullname, p.name, p.price, od.order.createAt " +
            "ORDER BY od.order.createAt DESC")
    List<ReportRevenue> findRevenueSummary();

}