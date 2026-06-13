package com.finsight.backend.service;

import com.finsight.backend.dto.BudgetRequest;
import com.finsight.backend.dto.BudgetStatusResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.BudgetRepository;
import com.finsight.backend.repository.TransactionRepository;
import com.finsight.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public BudgetService(BudgetRepository budgetRepository, UserRepository userRepository, TransactionRepository transactionRepository) {
        this.budgetRepository = budgetRepository;
        this.userRepository = userRepository;
        this.transactionRepository = transactionRepository;
    }

    public Budget createBudget(BudgetRequest budgetRequest) {
        if (budgetRequest.getUserId() == null) {
            throw new IllegalArgumentException("userId is required");
        }

        Optional<User> user = userRepository.findById(budgetRequest.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found with ID: " + budgetRequest.getUserId());
        }

        Budget budget = new Budget();
        budget.setCategory(budgetRequest.getCategory());
        budget.setMonthlyLimit(budgetRequest.getMonthlyLimit());
        budget.setUser(user.get());

        return budgetRepository.save(budget);
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public List<Budget> getBudgetsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
        return budgetRepository.findByUserId(userId);
    }

    /**
     * For each budget of the user, compute spent and remaining using transactions
     */
    public List<BudgetStatusResponse> getBudgetStatusForUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        List<Budget> budgets = budgetRepository.findByUserId(userId);
        List<BudgetStatusResponse> statusList = new ArrayList<>();

        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double limit = budget.getMonthlyLimit() != null ? budget.getMonthlyLimit() : 0.0;
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(userId, category);
            if (spent == null) {
                spent = 0.0;
            }
            Double remaining = limit - spent;
            statusList.add(new BudgetStatusResponse(category, limit, spent, remaining));
        }

        return statusList;
    }
}

