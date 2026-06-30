package com.expensetracker.service;

import com.expensetracker.dto.DashboardSummary;
import com.expensetracker.dto.TransactionRequest;
import com.expensetracker.dto.TransactionResponse;
import com.expensetracker.entity.Category;
import com.expensetracker.entity.Transaction;
import com.expensetracker.entity.User;
import com.expensetracker.exception.ResourceNotFoundException;
import com.expensetracker.repository.CategoryRepository;
import com.expensetracker.repository.TransactionRepository;
import com.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<TransactionResponse> getAllForUser(Long userId) {
        return transactionRepository.findByUserIdOrderByTransactionDateDesc(userId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public TransactionResponse getById(Long id, Long userId) {
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!t.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        return toResponse(t);
    }

    public TransactionResponse create(TransactionRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        Transaction t = new Transaction();
        t.setAmount(request.getAmount());
        t.setDescription(request.getDescription());
        t.setType(request.getType());
        t.setTransactionDate(request.getTransactionDate());
        t.setUser(user);
        t.setCategory(category);

        return toResponse(transactionRepository.save(t));
    }

    public TransactionResponse update(Long id, TransactionRequest request, Long userId) {
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!t.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        t.setAmount(request.getAmount());
        t.setDescription(request.getDescription());
        t.setType(request.getType());
        t.setTransactionDate(request.getTransactionDate());
        t.setCategory(category);

        return toResponse(transactionRepository.save(t));
    }

    public void delete(Long id, Long userId) {
        Transaction t = transactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
        if (!t.getUser().getId().equals(userId)) {
            throw new ResourceNotFoundException("Transaction not found");
        }
        transactionRepository.delete(t);
    }

    public List<TransactionResponse> getByCategory(Long userId, Long categoryId) {
        return transactionRepository.findByUserIdAndCategoryIdOrderByTransactionDateDesc(userId, categoryId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public DashboardSummary getDashboardSummary(Long userId) {
        List<Transaction> all = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);

        BigDecimal income = all.stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = all.stream()
                .filter(t -> "EXPENSE".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> expenseByCategory = new LinkedHashMap<>();
        all.stream()
                .filter(t -> "EXPENSE".equals(t.getType()) && t.getCategory() != null)
                .forEach(t -> expenseByCategory.merge(
                        t.getCategory().getName(), t.getAmount(), BigDecimal::add));

        List<TransactionResponse> recent = all.stream()
                .limit(8)
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new DashboardSummary(income, expense, income.subtract(expense), expenseByCategory, recent);
    }

    private TransactionResponse toResponse(Transaction t) {
        return new TransactionResponse(
                t.getId(),
                t.getAmount(),
                t.getDescription(),
                t.getType(),
                t.getTransactionDate(),
                t.getCategory() != null ? t.getCategory().getId() : null,
                t.getCategory() != null ? t.getCategory().getName() : null
        );
    }
}
