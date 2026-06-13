package com.finsight.backend.service;

import com.finsight.backend.dto.BudgetRequest;
import com.finsight.backend.dto.BudgetStatusResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.BudgetRepository;
import com.finsight.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    public Budget createBudget(BudgetRequest budgetRequest, User user) {
        Budget budget = new Budget();
        budget.setCategory(budgetRequest.getCategory());
        budget.setMonthlyLimit(budgetRequest.getMonthlyLimit());
        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    public List<Budget> getBudgetsByUser(User user) {
        return budgetRepository.findByUserId(user.getId());
    }

    public List<BudgetStatusResponse> getBudgetStatusForUser(User user) {
        List<Budget> budgets = getBudgetsByUser(user);
        List<BudgetStatusResponse> statusList = new ArrayList<>();

        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double limit = budget.getMonthlyLimit() != null ? budget.getMonthlyLimit() : 0.0;
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(user.getId(), category);
            if (spent == null) {
                spent = 0.0;
            }
            Double remaining = limit - spent;
            statusList.add(new BudgetStatusResponse(category, limit, spent, remaining));
        }

        return statusList;
    }
}