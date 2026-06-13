package com.finsight.backend.controller;

import com.finsight.backend.dto.BudgetRequest;
import com.finsight.backend.dto.BudgetStatusResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.entity.User;
import com.finsight.backend.service.AuthService;
import com.finsight.backend.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final AuthService authService;

    public BudgetController(BudgetService budgetService, AuthService authService) {
        this.budgetService = budgetService;
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest budgetRequest) {
        User user = authService.getAuthenticatedUser();
        Budget saved = budgetService.createBudget(budgetRequest, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        User user = authService.getAuthenticatedUser();
        return budgetService.getBudgetsByUser(user);
    }

    @GetMapping("/status")
    public List<BudgetStatusResponse> getBudgetStatus() {
        User user = authService.getAuthenticatedUser();
        return budgetService.getBudgetStatusForUser(user);
    }
}