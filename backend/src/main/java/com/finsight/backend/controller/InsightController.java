package com.finsight.backend.controller;

import com.finsight.backend.dto.InsightResponse;
import com.finsight.backend.service.InsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insights")
public class InsightController {

    private final InsightService insightService;

    public InsightController(InsightService insightService) {
        this.insightService = insightService;
    }

    /**
     * GET /api/insights/{userId}
     *
     * Generate AI insights based on the user's transaction and budget data.
     *
     * Insights include:
     * - Budget overspending alerts
     * - Savings rate metrics
     * - Category spending analysis
     *
     * Response:
     * {
     *   "insights": [
     *     "Your food spending exceeds budget by ₹800",
     *     "Great job! Your savings rate is above 40% - excellent financial health.",
     *     "Food accounts for 35% of your total expenses."
     *   ]
     * }
     */
    @GetMapping("/{userId}")
    public ResponseEntity<InsightResponse> getInsights(@PathVariable Long userId) {
        try {
            InsightResponse insights = insightService.generateInsights(userId);
            return ResponseEntity.ok(insights);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

