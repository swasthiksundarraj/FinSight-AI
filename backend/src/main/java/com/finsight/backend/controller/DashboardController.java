package com.finsight.backend.controller;

import com.finsight.backend.repository.TransactionRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final String TYPE_INCOME = "INCOME";
    private static final String TYPE_EXPENSE = "EXPENSE";

    private final TransactionRepository transactionRepository;

    public DashboardController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/total-income")
    public Map<String, Double> getTotalIncome() {
        Double totalIncome = transactionRepository.sumAmountByType(TYPE_INCOME);
        return Map.of("totalIncome", totalIncome);
    }

    @GetMapping("/total-expense")
    public Map<String, Double> getTotalExpense() {
        Double totalExpense = transactionRepository.sumAmountByType(TYPE_EXPENSE);
        return Map.of("totalExpense", totalExpense);
    }

    @GetMapping("/savings")
    public Map<String, Double> getSavings() {
        Double totalIncome = transactionRepository.sumAmountByType(TYPE_INCOME);
        Double totalExpense = transactionRepository.sumAmountByType(TYPE_EXPENSE);
        Double savings = totalIncome - totalExpense;
        return Map.of("savings", savings);
    }
}
