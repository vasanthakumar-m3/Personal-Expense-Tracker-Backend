package com.expensetracker.controller;

import com.expensetracker.dto.DashboardSummary;
import com.expensetracker.dto.TransactionRequest;
import com.expensetracker.dto.TransactionResponse;
import com.expensetracker.service.TransactionService;
import com.expensetracker.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SecurityUtil securityUtil;

    @GetMapping
    public ResponseEntity<List<TransactionResponse>> getAll() {
        return ResponseEntity.ok(transactionService.getAllForUser(securityUtil.getCurrentUserId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getById(id, securityUtil.getCurrentUserId()));
    }

    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        return new ResponseEntity<>(
                transactionService.create(request, securityUtil.getCurrentUserId()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        return ResponseEntity.ok(transactionService.update(id, request, securityUtil.getCurrentUserId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        transactionService.delete(id, securityUtil.getCurrentUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<TransactionResponse>> getByCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok(transactionService.getByCategory(securityUtil.getCurrentUserId(), categoryId));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardSummary> getDashboard() {
        return ResponseEntity.ok(transactionService.getDashboardSummary(securityUtil.getCurrentUserId()));
    }
}
