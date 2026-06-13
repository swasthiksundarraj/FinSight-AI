package com.finsight.backend.controller;

import com.finsight.backend.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/total-income")
    public Map<String, Double> getTotalIncome() {
        Double totalIncome = dashboardService.getTotalIncome();
        return Map.of("totalIncome", totalIncome);
    }

    @GetMapping("/total-expense")
    public Map<String, Double> getTotalExpense() {
        Double totalExpense = dashboardService.getTotalExpense();
        return Map.of("totalExpense", totalExpense);
    }

    @GetMapping("/savings")
    public Map<String, Double> getSavings() {
        Double savings = dashboardService.getSavings();
        return Map.of("savings", savings);
    }

}
