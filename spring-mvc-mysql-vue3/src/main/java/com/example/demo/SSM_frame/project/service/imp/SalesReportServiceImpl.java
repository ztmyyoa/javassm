package com.example.demo.SSM_frame.project.service.imp;

import com.example.demo.SSM_frame.project.map.SalesReportMapper;
import com.example.demo.SSM_frame.project.pojo.SalesReport;
import com.example.demo.SSM_frame.project.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalesReportServiceImpl implements SalesReportService {

    @Autowired
    private SalesReportMapper salesReportMapper;

    @Override
    public SalesReport getSalesReportById(int reportid) {
        return salesReportMapper.selectSalesReportById(reportid);
    }

    @Override
    public List<SalesReport> getAllSalesReports() {
        return salesReportMapper.selectAllSalesReports();
    }

    @Override
    public void addSalesReport(SalesReport salesReport) {
        salesReportMapper.insertSalesReport(salesReport);
    }

    @Override
    public void updateSalesReport(SalesReport salesReport) {
        salesReportMapper.updateSalesReport(salesReport);
    }

    @Override
    public void deleteSalesReport(int reportid) {
        salesReportMapper.deleteSalesReport(reportid);
    }
}
