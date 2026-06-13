package com.finsight.backend.service;

import com.finsight.backend.dto.InsightResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.entity.User;
import com.finsight.backend.repository.BudgetRepository;
import com.finsight.backend.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class InsightService {

    private final DashboardService dashboardService;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;

    public InsightService(DashboardService dashboardService,
                         BudgetRepository budgetRepository,
                         TransactionRepository transactionRepository) {
        this.dashboardService = dashboardService;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
    }

    public InsightResponse generateInsights(User user) {
        List<String> insights = new ArrayList<>();
        insights.addAll(checkBudgetExceeded(user));
        insights.addAll(checkSavingsRate(user));
        insights.addAll(analyzeCategorySpending(user));
        return new InsightResponse(insights);
    }

    private List<String> checkBudgetExceeded(User user) {
        List<String> insights = new ArrayList<>();
        List<Budget> budgets = budgetRepository.findByUserId(user.getId());

        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double limit = budget.getMonthlyLimit();
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(user.getId(), category);

            if (spent != null && spent > limit) {
                insights.add(String.format("Your %s spending exceeds budget by ₹%.0f.",
                        category.toLowerCase(), spent - limit));
            }
        }
        return insights;
    }

    private List<String> checkSavingsRate(User user) {
        List<String> insights = new ArrayList<>();
        Double totalIncome = dashboardService.getTotalIncome(user.getId());
        if (totalIncome == null || totalIncome == 0) return insights;

        Double savings = dashboardService.getSavings(user.getId());
        if (savings == null) return insights;

        double savingsRate = (savings / totalIncome) * 100;
        if (savingsRate >= 40) {
            insights.add("Great job! Your savings rate is above 40% - excellent financial health.");
        } else if (savingsRate >= 20) {
            insights.add("Your savings rate is above 20% - you're in good financial shape.");
        } else {
            insights.add("Consider increasing your savings rate to build better financial security.");
        }
        return insights;
    }

    private List<String> analyzeCategorySpending(User user) {
        List<String> insights = new ArrayList<>();
        Double totalExpense = dashboardService.getTotalExpense(user.getId());
        if (totalExpense == null || totalExpense == 0) return insights;

        List<Budget> budgets = budgetRepository.findByUserId(user.getId());
        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(user.getId(), category);
            if (spent != null && spent > 0) {
                double percentage = (spent / totalExpense) * 100;
                if (percentage > 30) {
                    insights.add(String.format("%s accounts for %.0f%% of your total expenses.",
                            category, percentage));
                }
            }
        }
        return insights;
    }
}