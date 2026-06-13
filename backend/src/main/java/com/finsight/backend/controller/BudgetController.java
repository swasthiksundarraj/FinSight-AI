package com.finsight.backend.controller;

import com.finsight.backend.dto.BudgetRequest;
import com.finsight.backend.dto.BudgetStatusResponse;
import com.finsight.backend.entity.Budget;
import com.finsight.backend.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public ResponseEntity<Budget> createBudget(@RequestBody BudgetRequest budgetRequest) {
        try {
            Budget saved = budgetService.createBudget(budgetRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Budget>> getBudgetsByUser(@PathVariable Long userId) {
        try {
            List<Budget> budgets = budgetService.getBudgetsByUserId(userId);
            return ResponseEntity.ok(budgets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<List<BudgetStatusResponse>> getBudgetStatus(@PathVariable Long userId) {
        try {
            List<BudgetStatusResponse> status = budgetService.getBudgetStatusForUser(userId);
            return ResponseEntity.ok(status);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}