package com.poly.quanlybanhang.repository;

import com.poly.quanlybanhang.dto.response.OrderDetailResponse;
import com.poly.quanlybanhang.entity.OrderDetails;
import com.poly.quanlybanhang.report.CustomerStatistics;
import com.poly.quanlybanhang.report.EmployeePerformance;
import com.poly.quanlybanhang.report.ProductRevenueStatistics;
import com.poly.quanlybanhang.report.SellHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, String> {

    @Query("SELECT new com.poly.quanlybanhang.report.SellHistory(" +
            "o.id, " +
            "o.fullname, " +
            "o.phone, " +
            "o.totalAmount, " +
            "o.status, " +
            "u.fullname, " +
            "o.createAt) " +
            "FROM Orders o JOIN o.user u " +
            "GROUP BY o.fullname, o.createAt " +
            "ORDER BY o.id DESC")
    List<SellHistory> findRevenueReport();


    @Query("SELECT new com.poly.quanlybanhang.report.CustomerStatistics(" +
            "o.fullname, " +
            "COUNT(o), " +
            "SUM(od.quantity), " +
            "SUM(od.price*od.quantity), " +
            "(SELECT p.name FROM OrderDetails od2 JOIN od2.product p WHERE od2.order.phone = o.phone GROUP BY p.name ORDER BY SUM(od2.quantity) DESC LIMIT 1), " +
            "MAX(o.createAt)) " +
            "FROM Orders o JOIN o.orderDetails od " +
            "GROUP BY o.fullname, o.phone " +
            "ORDER BY SUM(od.price) DESC")
    List<CustomerStatistics> findTopCustomersByOrderValue();

    @Query("SELECT new com.poly.quanlybanhang.report.CustomerStatistics(" +
            "o.fullname, " +
            "COUNT(o), " +
            "SUM(od.quantity), " +
            "SUM(od.price), " +
            "(SELECT p.name FROM OrderDetails od2 JOIN od2.product p WHERE od2.order.phone = o.phone GROUP BY p.name ORDER BY SUM(od2.quantity) DESC LIMIT 1), " +
            "MAX(o.createAt)) " +
            "FROM Orders o JOIN o.orderDetails od " +
            "GROUP BY o.fullname, o.phone " +
            "ORDER BY COUNT(o) DESC")
    List<CustomerStatistics> findTopCustomersByOrderCount();


    @Query("SELECT new com.poly.quanlybanhang.report.EmployeePerformance(" +
            "u.fullname, " +
            "COUNT(DISTINCT o.id), " +
            "SUM(od.price * od.quantity), " +
            "MAX(o.createAt)) " +
            "FROM Orders o " +
            "JOIN o.user u " +
            "JOIN o.orderDetails od " +
            "GROUP BY u.id, u.fullname " +
            "ORDER BY COUNT(DISTINCT o.id) DESC")
    List<EmployeePerformance> getEmployeePerformanceSummary();

    @Query("SELECT new com.poly.quanlybanhang.report.ProductRevenueStatistics(" +
            "p.name, " +
            "SUM(od.quantity), " +
            "p.price, " +
            "SUM(od.quantity * od.price), " +
            "CAST(od.createAt AS LocalDate)) " +
            "FROM OrderDetails od JOIN od.product p " +
            "GROUP BY p.name, p.price, CAST(od.createAt AS LocalDate) " +
            "ORDER BY CAST(od.createAt AS LocalDate) DESC")
    List<ProductRevenueStatistics> findProductRevenueByAllDates();

    @Query("SELECT SUM(od.quantity * od.price) FROM OrderDetails od")
    Double findTotalRevenue();

    @Query("SELECT COUNT(p.name) FROM Products p")
    Long findTotalQuantityProduct();

    @Query("SELECT COUNT(DISTINCT o.fullname) FROM Orders o")
    Long findTotalCustomers();

    @Query("SELECT od FROM OrderDetails od WHERE od.order.id = :orderId")
    List<OrderDetails> findOrderDetailsByOrderId(@Param("orderId") Integer orderId);


}