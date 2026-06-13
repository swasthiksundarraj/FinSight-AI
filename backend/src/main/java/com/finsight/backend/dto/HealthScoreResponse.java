package com.finsight.backend.dto;

public class HealthScoreResponse {

    private Double totalIncome;
    private Double totalExpense;
    private Double savings;
    private Double savingsRate;
    private Integer healthScore;

    public HealthScoreResponse(Double totalIncome, Double totalExpense,
                              Double savings, Double savingsRate, Integer healthScore) {
        this.totalIncome = totalIncome;
        this.totalExpense = totalExpense;
        this.savings = savings;
        this.savingsRate = savingsRate;
        this.healthScore = healthScore;
    }

    public HealthScoreResponse() {
    }

    public Double getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Double totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Double getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Double totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Double getSavings() {
        return savings;
    }

    public void setSavings(Double savings) {
        this.savings = savings;
    }

    public Double getSavingsRate() {
        return savingsRate;
    }

    public void setSavingsRate(Double savingsRate) {
        this.savingsRate = savingsRate;
    }

    public Integer getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(Integer healthScore) {
        this.healthScore = healthScore;
    }

    @Override
    public String toString() {
        return "HealthScoreResponse{" +
                "totalIncome=" + totalIncome +
                ", totalExpense=" + totalExpense +
                ", savings=" + savings +
                ", savingsRate=" + savingsRate +
                ", healthScore=" + healthScore +
                '}';
    }
}

