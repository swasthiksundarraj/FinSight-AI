package com.finsight.backend.service;

import com.finsight.backend.dto.HealthScoreResponse;
import org.springframework.stereotype.Service;

@Service
public class HealthScoreService {

    private final DashboardService dashboardService;

    public HealthScoreService(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * Calculate the Financial Health Score based on savings rate.
     *
     * Formula:
     * - Savings = Income - Expense
     * - Savings Rate = ((Income - Expense) / Income) * 100
     *
     * Health Score Rules:
     * - Savings Rate >= 40 => 90
     * - Savings Rate >= 30 => 80
     * - Savings Rate >= 20 => 70
     * - Savings Rate >= 10 => 60
     * - Else => 50
     */
    public HealthScoreResponse calculateHealthScore() {
        // Get financial data from DashboardService
        Double totalIncome = dashboardService.getTotalIncome();
        Double totalExpense = dashboardService.getTotalExpense();
        Double savings = dashboardService.getSavings();

        // Calculate Savings Rate
        Double savingsRate = calculateSavingsRate(totalIncome, savings);

        // Calculate Health Score based on Savings Rate
        Integer healthScore = calculateScoreFromSavingsRate(savingsRate);

        // Return response
        return new HealthScoreResponse(totalIncome, totalExpense, savings, savingsRate, healthScore);
    }

    /**
     * Calculate Savings Rate based on income and savings.
     * Formula: ((Income - Expense) / Income) * 100
     * Or simply: (Savings / Income) * 100
     *
     * Edge case: If income is 0, return 0
     */
    private Double calculateSavingsRate(Double totalIncome, Double savings) {
        if (totalIncome == null || totalIncome == 0 || totalIncome < 0) {
            return 0.0;
        }

        if (savings == null) {
            savings = 0.0;
        }

        // Calculate percentage
        Double rate = (savings / totalIncome) * 100;

        // Round to 2 decimal places
        return Math.round(rate * 100.0) / 100.0;
    }

    /**
     * Calculate Health Score based on Savings Rate.
     *
     * Rules:
     * - Savings Rate >= 40 => 90
     * - Savings Rate >= 30 => 80
     * - Savings Rate >= 20 => 70
     * - Savings Rate >= 10 => 60
     * - Else => 50
     */
    private Integer calculateScoreFromSavingsRate(Double savingsRate) {
        if (savingsRate == null) {
            savingsRate = 0.0;
        }

        if (savingsRate >= 40) {
            return 90;
        } else if (savingsRate >= 30) {
            return 80;
        } else if (savingsRate >= 20) {
            return 70;
        } else if (savingsRate >= 10) {
            return 60;
        } else {
            return 50;
        }
    }
}

