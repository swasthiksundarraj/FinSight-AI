package com.finsight.backend.controller;

import com.finsight.backend.dto.HealthScoreResponse;
import com.finsight.backend.entity.User;
import com.finsight.backend.service.AuthService;
import com.finsight.backend.service.HealthScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/health-score")
public class HealthScoreController {

    private final HealthScoreService healthScoreService;
    private final AuthService authService;

    public HealthScoreController(HealthScoreService healthScoreService, AuthService authService) {
        this.healthScoreService = healthScoreService;
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<HealthScoreResponse> getHealthScore() {
        User user = authService.getAuthenticatedUser();
        HealthScoreResponse healthScore = healthScoreService.calculateHealthScore(user);
        return ResponseEntity.ok(healthScore);
    }
}