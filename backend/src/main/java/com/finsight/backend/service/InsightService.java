package com.finsight.backend.service;

import com.finsight.backend.dto.InsightResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.repository.BudgetRepository;
import com.finsight.backend.repository.TransactionRepository;
import com.finsight.backend.repository.UserRepository;
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
    private final UserRepository userRepository;

    public InsightService(DashboardService dashboardService,
                         BudgetRepository budgetRepository,
                         TransactionRepository transactionRepository,
                         UserRepository userRepository) {
        this.dashboardService = dashboardService;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    /**
     * Generate AI insights based on transaction and budget data.
     * Rule-based insights include:
     * 1. Budget overspending alerts
     * 2. Savings rate metrics
     * 3. Category spending analysis
     */
    public InsightResponse generateInsights(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }

        List<String> insights = new ArrayList<>();

        // 1. Check budget exceeded for each category
        insights.addAll(checkBudgetExceeded(userId));

        // 2. Check savings rate and generate insights
        insights.addAll(checkSavingsRate());

        // 3. Analyze category spending as percentage of total
        insights.addAll(analyzeCategorySpending(userId));

        return new InsightResponse(insights);
    }

    /**
     * Rule: Check if spending in any category exceeds its budget
     */
    private List<String> checkBudgetExceeded(Long userId) {
        List<String> insights = new ArrayList<>();
        List<Budget> budgets = budgetRepository.findByUserId(userId);

        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double limit = budget.getMonthlyLimit();
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(userId, category);

            if (spent == null) {
                spent = 0.0;
            }

            if (spent > limit) {
                Double exceeded = spent - limit;
                insights.add(String.format("Your %s spending exceeds budget by ₹%.0f.",
                        category.toLowerCase(), exceeded));
            }
        }

        return insights;
    }

    /**
     * Rule: Analyze savings rate and generate insights
     */
    private List<String> checkSavingsRate() {
        List<String> insights = new ArrayList<>();

        Double totalIncome = dashboardService.getTotalIncome();
        if (totalIncome == null || totalIncome == 0) {
            return insights;
        }

        Double savings = dashboardService.getSavings();
        if (savings == null) {
            savings = 0.0;
        }

        Double savingsRate = (savings / totalIncome) * 100;

        if (savingsRate >= 40) {
            insights.add("Great job! Your savings rate is above 40% - excellent financial health.");
        } else if (savingsRate >= 30) {
            insights.add("Your savings rate is above 30% - you're in good financial shape.");
        } else if (savingsRate >= 20) {
            insights.add("Your savings rate is above 20% - keep up the good work.");
        } else if (savingsRate >= 10) {
            insights.add("Consider increasing your savings rate beyond 10% to build better financial security.");
        } else {
            insights.add("Your savings rate is below 10% - consider reducing expenses to increase savings.");
        }

        return insights;
    }

    /**
     * Rule: Analyze which category accounts for the highest percentage of total expenses
     */
    private List<String> analyzeCategorySpending(Long userId) {
        List<String> insights = new ArrayList<>();

        Double totalExpense = dashboardService.getTotalExpense();
        if (totalExpense == null || totalExpense == 0) {
            return insights;
        }

        List<Budget> budgets = budgetRepository.findByUserId(userId);
        Map<String, Double> categorySpending = new HashMap<>();
        Double maxSpent = 0.0;
        String maxCategory = "";

        for (Budget budget : budgets) {
            String category = budget.getCategory();
            Double spent = transactionRepository.sumAmountByUserIdAndCategory(userId, category);

            if (spent == null) {
                spent = 0.0;
            }

            categorySpending.put(category, spent);
            if (spent > maxSpent) {
                maxSpent = spent;
                maxCategory = category;
            }
        }

        if (maxSpent > 0) {
            Double percentage = (maxSpent / totalExpense) * 100;
            insights.add(String.format("%s accounts for %.0f%% of your total expenses.",
                    maxCategory, percentage));
        }

        // Check for high spending categories (>30% of expenses)
        for (Map.Entry<String, Double> entry : categorySpending.entrySet()) {
            if (entry.getValue() > 0) {
                Double percentage = (entry.getValue() / totalExpense) * 100;
                if (percentage > 30 && !entry.getKey().equals(maxCategory)) {
                    insights.add(String.format("%s is a significant expense category at %.0f%% of your spending.",
                            entry.getKey(), percentage));
                }
            }
        }

        return insights;
    }
}

