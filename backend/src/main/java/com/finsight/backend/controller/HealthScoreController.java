package com.finsight.backend.controller;

import com.finsight.backend.dto.HealthScoreResponse;
import com.finsight.backend.service.HealthScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-score")
public class HealthScoreController {

    private final HealthScoreService healthScoreService;

    public HealthScoreController(HealthScoreService healthScoreService) {
        this.healthScoreService = healthScoreService;
    }

    /**
     * GET /api/health-score
     *
     * Calculate and return the Financial Health Score based on income, expenses, and savings rate.
     *
     * Response:
     * {
     *   "totalIncome": 50000,
     *   "totalExpense": 30000,
     *   "savings": 20000,
     *   "savingsRate": 40.0,
     *   "healthScore": 90
     * }
     */
    @GetMapping
    public ResponseEntity<HealthScoreResponse> getHealthScore() {
        HealthScoreResponse healthScore = healthScoreService.calculateHealthScore();
        return ResponseEntity.ok(healthScore);
    }
}

