package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.SalesReport;

import java.util.List;

public interface SalesReportService {
    SalesReport getSalesReportById(int reportid);
    List<SalesReport> getAllSalesReports();
    void addSalesReport(SalesReport salesReport);
    void updateSalesReport(SalesReport salesReport);
    void deleteSalesReport(int reportid);
}
