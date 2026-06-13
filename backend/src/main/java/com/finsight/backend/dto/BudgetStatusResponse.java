package com.finsight.backend.dto;

public class BudgetStatusResponse {

    private String category;
    private Double limit;
    private Double spent;
    private Double remaining;

    public BudgetStatusResponse() {
    }

    public BudgetStatusResponse(String category, Double limit, Double spent, Double remaining) {
        this.category = category;
        this.limit = limit;
        this.spent = spent;
        this.remaining = remaining;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getLimit() {
        return limit;
    }

    public void setLimit(Double limit) {
        this.limit = limit;
    }

    public Double getSpent() {
        return spent;
    }

    public void setSpent(Double spent) {
        this.spent = spent;
    }

    public Double getRemaining() {
        return remaining;
    }

    public void setRemaining(Double remaining) {
        this.remaining = remaining;
    }
}

