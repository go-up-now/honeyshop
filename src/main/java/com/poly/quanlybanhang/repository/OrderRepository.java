package com.poly.quanlybanhang.repository;

import com.poly.quanlybanhang.entity.Orders;
import com.poly.quanlybanhang.statistical.AgeOfProductConsumption;
import com.poly.quanlybanhang.statistical.GenderOfProductConsumption;
import com.poly.quanlybanhang.statistical.SalesTimeFrame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Orders, String> {
    Optional<Orders> findTopByPhoneOrderByCreateAtDesc(String phone);

    @Query("SELECT " +
            "new com.poly.quanlybanhang.statistical.AgeOfProductConsumption(" +
            "CASE " +
            "WHEN o.age BETWEEN 18 AND 24 THEN '18-24' " +
            "WHEN o.age BETWEEN 25 AND 34 THEN '25-34' " +
            "ELSE '35+' END, " +
            "p.name, " +
            "c.name, " +
            "SUM(od.quantity), " +
            "SUM(od.price * od.quantity), " +
            "(SUM(od.price * od.quantity) / ((SELECT SUM(odt.price * odt.quantity) FROM OrderDetails odt)) * 100.0))" +  // Assuming you want percentage of total sales
            "FROM Orders o JOIN o.orderDetails od JOIN od.product p JOIN p.categories c " +
            "GROUP BY " +
            "CASE " +
            "WHEN o.age BETWEEN 18 AND 24 THEN '18-24' " +
            "WHEN o.age BETWEEN 25 AND 34 THEN '25-34' " +
            "ELSE '35+' END, " +
            "p.name, " +
            "c.name")
    List<AgeOfProductConsumption> getAgeOfProductConsumption();

    @Query("SELECT " +
            "new com.poly.quanlybanhang.statistical.GenderOfProductConsumption(" +
            "CASE " +
            "WHEN o.gender = true THEN 'Nam' " +
            "WHEN o.gender = false THEN 'Nữ' " +
            "ELSE 'Khác' END, " +
            "p.name, " +
            "c.name, " +
            "SUM(od.quantity), " +
            "SUM(od.price * od.quantity), " +
            "(SUM(od.price * od.quantity) / ((SELECT SUM(odt.price * odt.quantity) FROM OrderDetails odt)) * 100.0))" +  // Assuming you want percentage of total sales
            "FROM Orders o JOIN o.orderDetails od JOIN od.product p JOIN p.categories c " +
            "GROUP BY " +
            "CASE " +
            "WHEN o.gender = true THEN 'Nam' " +
            "WHEN o.gender = false THEN 'Nữ' " +
            "ELSE 'Khác' END, " +
            "p.name, " +
            "c.name")
    List<GenderOfProductConsumption> getGenderOfProductConsumption();

//    @Query("SELECT " +
//            "new com.poly.quanlybanhang.statistical.SalesTimeFrame(" +
//            "DATE(o.createAt), " +
//            "CASE " +
//            "        WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
//            "        ELSE 'Buổi tối' END, " +
//            "p.name, " +
//            "c.name, " +
//            "SUM(od.quantity), " +
//            "SUM(od.price * od.quantity), " +
//            "(SUM(od.price * od.quantity) / ((SELECT SUM(odt.price * odt.quantity) FROM OrderDetails odt WHERE DATE(odt.order.createAt) = DATE(o.createAt)))) * 100.0)" +  // Assuming you want percentage of total sales
//            "FROM Orders o JOIN o.orderDetails od JOIN od.product p JOIN p.categories c " +
//            "GROUP BY DATE(o.createAt)," +
//            "CASE " +
//            "        WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
//            "        ELSE 'Buổi tối' END, " +
//            "p.name, " +
//            "c.name")
//    List<SalesTimeFrame> getSalesTimeFrame();


//    @Query("SELECT NEW com.poly.quanlybanhang.statistical.SalesTimeFrame(" +
//            "    DATE(o.createAt), " +
//            "    CASE " +
//            "        WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
//            "        WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
//            "        ELSE 'Buổi tối' " +
//            "    END, " +
//            "    p.name, " +
//            "    c.name, " +
//            "    SUM(od.quantity), " +
//            "    SUM(od.price * od.quantity), " +
//            "   (SUM(od.price * od.quantity) / ((SELECT SUM(odt.price * odt.quantity) FROM OrderDetails odt WHERE DATE(odt.order.createAt) = DATE(o.createAt)))) * 100.0)" +
////            "    (SUM(od.price * od.quantity) / " +
////            "     (SELECT SUM(odt.price * odt.quantity) " +
////            "      FROM OrderDetails odt " +
////            "      WHERE DATE(odt.order.createAt) = DATE(o.createAt)" +
////            "     )" +
////            "    )))* 100.0" +
////            ") " +
//            "FROM Orders o JOIN o.orderDetails od JOIN od.product p JOIN p.categories c " +
//            "GROUP BY " +
//            "DATE(o.createAt), " +
//            "HOUR(o.createAt), " +
//            "DATE(od.order.createAt), " +
////            "o.createAt, " +
//            "         CASE " +
//            "             WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
//            "             WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
//            "             WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
//            "             ELSE 'Buổi tối' " +
//            "         END, " +
//            "         p.name, " +
//            "         c.name")
//    List<SalesTimeFrame> getSalesTimeFrame();


    @Query("SELECT NEW com.poly.quanlybanhang.statistical.SalesTimeFrame(" +
            "    DATE(o.createAt), " +
            "    CASE " +
            "        WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
            "        WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
            "        WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
            "        ELSE 'Buổi tối' " +
            "    END, " +
            "    p.name, " +
            "    c.name, " +
            "    SUM(od.quantity), " +
            "    SUM(od.price * od.quantity), " +
            "    (SUM(od.price * od.quantity) / " +
            "     (SELECT SUM(odt.price * odt.quantity) " +
            "      FROM OrderDetails odt " +
            "      JOIN odt.order o2 " +
            "      WHERE DATE(o2.createAt) = DATE(o.createAt)) * 100.0) " +
            ") " +
            "FROM Orders o " +
            "JOIN o.orderDetails od " +
            "JOIN od.product p " +
            "JOIN p.categories c " +
            "GROUP BY DATE(o.createAt), " +
//            "o.createAt, " +
            "         CASE " +
            "             WHEN HOUR(o.createAt) BETWEEN 6 AND 10 THEN 'Buổi sáng' " +
            "             WHEN HOUR(o.createAt) BETWEEN 11 AND 13 THEN 'Buổi trưa' " +
            "             WHEN HOUR(o.createAt) BETWEEN 14 AND 18 THEN 'Buổi chiều' " +
            "             ELSE 'Buổi tối' " +
            "         END, " +
            "         p.name, " +
            "         c.name")
    List<SalesTimeFrame> getSalesTimeFrame();






}
