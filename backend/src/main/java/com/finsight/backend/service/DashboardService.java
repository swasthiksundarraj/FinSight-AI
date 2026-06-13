package com.finsight.backend.service;

import com.finsight.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private static final String TYPE_INCOME = "INCOME";
    private static final String TYPE_EXPENSE = "EXPENSE";

    private final TransactionRepository transactionRepository;

    public DashboardService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Double getTotalIncome(Long userId) {
        return transactionRepository.sumAmountByTypeAndUserId(TYPE_INCOME, userId);
    }

    public Double getTotalExpense(Long userId) {
        return transactionRepository.sumAmountByTypeAndUserId(TYPE_EXPENSE, userId);
    }

    public Double getSavings(Long userId) {
        Double income = getTotalIncome(userId);
        Double expense = getTotalExpense(userId);
        return income - expense;
    }
}