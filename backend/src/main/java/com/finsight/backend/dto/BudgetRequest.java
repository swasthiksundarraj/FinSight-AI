package com.finsight.backend.dto;

public class BudgetRequest {

    private String category;
    private Double monthlyLimit;
    private Long userId;

    public BudgetRequest() {
    }

    public BudgetRequest(String category, Double monthlyLimit, Long userId) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
        this.userId = userId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(Double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

