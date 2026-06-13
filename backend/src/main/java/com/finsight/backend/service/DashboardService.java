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

    public Double getTotalIncome() {
        return transactionRepository.sumAmountByType(TYPE_INCOME);
    }

    public Double getTotalExpense() {
        return transactionRepository.sumAmountByType(TYPE_EXPENSE);
    }

    public Double getSavings() {
        Double income = getTotalIncome();
        Double expense = getTotalExpense();
        return income - expense;
    }
}

