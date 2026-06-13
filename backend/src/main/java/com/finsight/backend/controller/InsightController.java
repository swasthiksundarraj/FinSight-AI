package com.finsight.backend.controller;

import com.finsight.backend.dto.InsightResponse;
import com.finsight.backend.entity.User;
import com.finsight.backend.service.AuthService;
import com.finsight.backend.service.InsightService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/insights")
public class InsightController {

    private final InsightService insightService;
    private final AuthService authService;

    public InsightController(InsightService insightService, AuthService authService) {
        this.insightService = insightService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<InsightResponse> getInsights() {
        User user = authService.getAuthenticatedUser();
        InsightResponse insights = insightService.generateInsights(user);
        return ResponseEntity.ok(insights);
    }
}