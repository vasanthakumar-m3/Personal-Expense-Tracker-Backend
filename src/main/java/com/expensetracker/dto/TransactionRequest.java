package com.expensetracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TransactionRequest {
    @NotNull(message = "Amount is required")
    private BigDecimal amount;

    private String description;

    @NotBlank(message = "Type is required")
    private String type; // INCOME or EXPENSE

    @NotNull(message = "Date is required")
    private LocalDate transactionDate;

    @NotNull(message = "Category is required")
    private Long categoryId;
}
