package com.poly.quanlybanhang.service.impl;

import com.poly.quanlybanhang.report.ReportRevenue;
import com.poly.quanlybanhang.repository.OrderDetailRepository;
import com.poly.quanlybanhang.service.ReportRevenuService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ReportRevenueServiceImpl implements ReportRevenuService {

    OrderDetailRepository orderDetailRepository;

    @Override
    public List<ReportRevenue> getAllRevenueReport() {
        return orderDetailRepository.findRevenueSummary();
    }
}
