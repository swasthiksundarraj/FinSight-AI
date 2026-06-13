package com.finsight.backend.service;

import com.finsight.backend.dto.HealthScoreResponse;
import com.finsight.backend.entity.User;
import org.springframework.stereotype.Service;

@Service
public class HealthScoreService {

    private final DashboardService dashboardService;

    public HealthScoreService(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    public HealthScoreResponse calculateHealthScore(User user) {
        Double totalIncome = dashboardService.getTotalIncome(user.getId());
        Double totalExpense = dashboardService.getTotalExpense(user.getId());
        Double savings = dashboardService.getSavings(user.getId());

        Double savingsRate = calculateSavingsRate(totalIncome, savings);
        Integer healthScore = calculateScoreFromSavingsRate(savingsRate);

        return new HealthScoreResponse(totalIncome, totalExpense, savings, savingsRate, healthScore);
    }

    private Double calculateSavingsRate(Double totalIncome, Double savings) {
        if (totalIncome == null || totalIncome == 0 || totalIncome < 0) {
            return 0.0;
        }

        if (savings == null) {
            savings = 0.0;
        }

        Double rate = (savings / totalIncome) * 100;
        return Math.round(rate * 100.0) / 100.0;
    }

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