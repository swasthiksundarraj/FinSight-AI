package com.finsight.backend.controller;

import com.finsight.backend.entity.User;
import com.finsight.backend.service.AuthService;
import com.finsight.backend.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AuthService authService;

    public DashboardController(DashboardService dashboardService, AuthService authService) {
        this.dashboardService = dashboardService;
        this.authService = authService;
    }

    @GetMapping("/total-income")
    public Map<String, Double> getTotalIncome() {
        User user = authService.getAuthenticatedUser();
        Double totalIncome = dashboardService.getTotalIncome(user.getId());
        return Map.of("totalIncome", totalIncome);
    }

    @GetMapping("/total-expense")
    public Map<String, Double> getTotalExpense() {
        User user = authService.getAuthenticatedUser();
        Double totalExpense = dashboardService.getTotalExpense(user.getId());
        return Map.of("totalExpense", totalExpense);
    }

    @GetMapping("/savings")
    public Map<String, Double> getSavings() {
        User user = authService.getAuthenticatedUser();
        Double savings = dashboardService.getSavings(user.getId());
        return Map.of("savings", savings);
    }
}