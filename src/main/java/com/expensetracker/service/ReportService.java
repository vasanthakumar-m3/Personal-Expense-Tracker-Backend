package com.expensetracker.service;

import com.expensetracker.entity.Transaction;
import com.expensetracker.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> getMonthlyReport(Long userId, int year, int month) {
        YearMonth ym = YearMonth.of(year, month);
        LocalDate start = ym.atDay(1);
        LocalDate end = ym.atEndOfMonth();

        List<Transaction> txns = transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end);

        BigDecimal income = txns.stream()
                .filter(t -> "INCOME".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal expense = txns.stream()
                .filter(t -> "EXPENSE".equals(t.getType()))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> report = new LinkedHashMap<>();
        report.put("year", year);
        report.put("month", month);
        report.put("totalIncome", income);
        report.put("totalExpense", expense);
        report.put("balance", income.subtract(expense));
        report.put("transactionCount", txns.size());
        return report;
    }

    public Map<String, BigDecimal> getCategoryReport(Long userId) {
        List<Transaction> txns = transactionRepository.findByUserIdOrderByTransactionDateDesc(userId);
        Map<String, BigDecimal> result = new LinkedHashMap<>();
        txns.stream()
                .filter(t -> t.getCategory() != null)
                .forEach(t -> result.merge(t.getCategory().getName(), t.getAmount(), BigDecimal::add));
        return result;
    }

    public Map<String, Object> getYearlyReport(Long userId, int year) {
        LocalDate start = LocalDate.of(year, 1, 1);
        LocalDate end = LocalDate.of(year, 12, 31);

        List<Transaction> txns = transactionRepository.findByUserIdAndTransactionDateBetween(userId, start, end);

        Map<Integer, BigDecimal> monthlyIncome = new LinkedHashMap<>();
        Map<Integer, BigDecimal> monthlyExpense = new LinkedHashMap<>();

        for (Transaction t : txns) {
            int month = t.getTransactionDate().getMonthValue();
            if ("INCOME".equals(t.getType())) {
                monthlyIncome.merge(month, t.getAmount(), BigDecimal::add);
            } else {
                monthlyExpense.merge(month, t.getAmount(), BigDecimal::add);
            }
        }

        Map<String, Object> report = new LinkedHashMap<>();
        report.put("year", year);
        report.put("monthlyIncome", monthlyIncome);
        report.put("monthlyExpense", monthlyExpense);
        return report;
    }
}
