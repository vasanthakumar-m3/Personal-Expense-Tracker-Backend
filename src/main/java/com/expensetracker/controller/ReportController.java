package com.expensetracker.controller;

import com.expensetracker.service.ReportService;
import com.expensetracker.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping("/monthly")
    public ResponseEntity<Map<String, Object>> monthly(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {
        LocalDate now = LocalDate.now();
        int y = year != null ? year : now.getYear();
        int m = month != null ? month : now.getMonthValue();
        return ResponseEntity.ok(reportService.getMonthlyReport(securityUtil.getCurrentUserId(), y, m));
    }

    @GetMapping("/category")
    public ResponseEntity<Map<String, BigDecimal>> category() {
        return ResponseEntity.ok(reportService.getCategoryReport(securityUtil.getCurrentUserId()));
    }

    @GetMapping("/yearly")
    public ResponseEntity<Map<String, Object>> yearly(@RequestParam(required = false) Integer year) {
        int y = year != null ? year : LocalDate.now().getYear();
        return ResponseEntity.ok(reportService.getYearlyReport(securityUtil.getCurrentUserId(), y));
    }
}
