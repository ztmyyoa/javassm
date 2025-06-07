package com.example.demo.SSM_frame.project.control;

import com.example.demo.SSM_frame.project.pojo.SalesReport;
import com.example.demo.SSM_frame.project.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/salesreport")
public class SalesReportController {

    @Autowired
    private SalesReportService salesReportService;

    @GetMapping("/report/{reportid}")
    public ResponseEntity<SalesReport> getSalesReportById(@PathVariable int reportid) {
        SalesReport salesReport = salesReportService.getSalesReportById(reportid);
        return ResponseEntity.ok(salesReport);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SalesReport>> getAllSalesReports() {
        List<SalesReport> salesReports = salesReportService.getAllSalesReports();
        return ResponseEntity.ok(salesReports);
    }

    @PostMapping("/add")
    public ResponseEntity<SalesReport> addSalesReport(@RequestBody SalesReport salesReport) {
        salesReportService.addSalesReport(salesReport);
        return ResponseEntity.status(HttpStatus.CREATED).body(salesReport);
    }

    @PutMapping("/update")
    public ResponseEntity<SalesReport> updateSalesReport(@RequestBody SalesReport salesReport) {
        salesReportService.updateSalesReport(salesReport);
        return ResponseEntity.ok(salesReport);
    }

    @DeleteMapping("/delete/{reportid}")
    public ResponseEntity<Void> deleteSalesReport(@PathVariable int reportid) {
        salesReportService.deleteSalesReport(reportid);
        return ResponseEntity.noContent().build();
    }
}
